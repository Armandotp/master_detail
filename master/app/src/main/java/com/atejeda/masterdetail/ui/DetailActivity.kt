package com.atejeda.masterdetail.ui

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.atejeda.masterdetail.R
import com.atejeda.masterdetail.data.model.Pokemon
import com.atejeda.masterdetail.databinding.ActivityDetailBinding
import com.atejeda.masterdetail.utils.load

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Detail"
        var pokemon = intent.getSerializableExtra("pokemon") as Pokemon
        var position = intent.getIntExtra("position",0)

        binding.fav.setOnCheckedChangeListener { _, isChecked ->
                val intent = Intent("fav")
                intent.putExtra("element", position)
                intent.putExtra("isChecked", isChecked)
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }

        setViews(pokemon)

    }

    fun setViews(element:Pokemon){
        binding.image.load(element.sprites?.front_default!!,element.name)
        binding.name.text = element.name
        binding.height.text = element.height.toString()
        binding.weight.text = element.weight.toString()
        binding.fav.isChecked = element.isFavourite

        val lparams = LayoutParams(
            LayoutParams.WRAP_CONTENT-50, LayoutParams.WRAP_CONTENT-50
        )

        element.typesString?.replace("[","")?.
                replace("]","")?.
                split(",")?.mapIndexed { index, s ->

            val tv = TextView(this)
            tv.setBackgroundResource(R.drawable.tag)
            tv.layoutParams = lparams
            tv.text = s

            tv.setPadding(8)
            tv.setTextColor(this.getColor(R.color.white))
            binding.parent.addView(tv)

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}