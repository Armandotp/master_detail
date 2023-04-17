package com.atejeda.masterdetail.core.mapper

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}