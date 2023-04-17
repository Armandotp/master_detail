package com.atejeda.masterdetail.utils


import android.Manifest
import android.R.attr.theme
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.atejeda.masterdetail.R
import com.bumptech.glide.Glide


fun ImageView.load(url:String?,name:String){
    if(!url.isNullOrEmpty()){
        var icon =
        if(name.isNullOrEmpty()){
            ResourcesCompat.getDrawable(resources, R.mipmap.ic_profile,null)
        }else{
            loadTextImage(name,resources)
        }
        Glide.with(this.context).load(url).error(icon).into(this)
    }else{
        var icon =
            if(name.isNullOrEmpty()){
                ResourcesCompat.getDrawable(resources, R.mipmap.ic_profile,null)
            }else{
                loadTextImage(name,resources)
            }
        this.setImageDrawable(icon)
    }
}

private fun loadTextImage(name: String,resources: Resources): Drawable {
    if(!name.first().toString().isLettersOnly()) return ResourcesCompat.getDrawable(resources, R.mipmap.ic_profile,null)!!
    var array = name.split(" ")
    var letters = ""
    array.forEach {
        if(it.isLettersOnly()){
            letters += it.first().uppercase()
        }
    }
    if(letters.isNullOrEmpty()) return ResourcesCompat.getDrawable(resources, R.mipmap.ic_profile,null)!!

    val generator: ColorGenerator = ColorGenerator.MATERIAL
    val color: Int = generator.getColor(name)
    val firstLetter = "$letters"
    return TextDrawable.builder()
        .buildRound(firstLetter, color)
}

fun String.isLettersOnly(): Boolean {
    val len = this.length
    for (i in 0 until len) {
        if (!this[i].isLetter()) {
            return false
        }
    }
    return true
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}
