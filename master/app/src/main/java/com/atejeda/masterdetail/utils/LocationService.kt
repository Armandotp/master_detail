package com.atejeda.masterdetail.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.atejeda.masterdetail.R
import com.atejeda.masterdetail.ui.interfaces.LocationClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private val db = FirebaseFirestore.getInstance()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Constants.ACTION_START -> start()
            Constants.ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        try{
            val channelID = getString(R.string.app_name)
            val notification = NotificationCompat.Builder(this, channelID)
                .setContentTitle("location...")
                .setContentText("Location: null")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setOngoing(true)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channel = NotificationChannel(channelID,getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }

            locationClient
                .getLocationUpdates(Constants.INTERVAL)
                .catch { e -> e.printStackTrace() }
                .onEach { location ->
                    val lat = location.latitude.toString()
                    val long = location.longitude.toString()
                    val updatedNotification = notification.setContentText(
                        "Location: ($lat, $long)"
                    )
                    db.collection(Constants.DB_FIRESTORE)
                        .add(
                            hashMapOf(
                                "lat" to location.latitude,
                                "long" to location.longitude,
                                "date" to getDateTime()
                            )
                        )
                    notificationManager.notify(1, updatedNotification.build())
                }
                .launchIn(serviceScope)

            startForeground(1, notification.build())
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun getDateTime(): String {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return df.format(Calendar.getInstance().time)
    }
}