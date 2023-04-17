package com.atejeda.masterdetail.ui.interfaces

import com.atejeda.masterdetail.data.model.Pokemon


interface ElementEvents {

    fun onclickElement(element: Pokemon, postion:Int,)
    fun onclickFavourite(element: Pokemon,postion:Int,checked:Boolean)

}