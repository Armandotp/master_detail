package com.atejeda.masterdetail.utils


import android.R.attr.theme
import android.graphics.drawable.Drawable
import android.widget.ImageView
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
            loadTextImage(name)
        }
        Glide.with(this.context).load(url).error(icon).into(this)
    }else{
        var icon =
            if(name.isNullOrEmpty()){
                ResourcesCompat.getDrawable(resources, R.mipmap.ic_profile,null)
            }else{
                loadTextImage(name)
            }
        this.setImageDrawable(icon)
    }
}

private fun loadTextImage(name: String): Drawable {
    var array = name.split(" ")
    var letters = ""
    array.forEach {
        letters += it.first().uppercase()
    }
    val generator: ColorGenerator = ColorGenerator.MATERIAL
    val color: Int = generator.getColor(name)
    val firstLetter = "$letters"
    return TextDrawable.builder()
        .buildRound(firstLetter, color)
}