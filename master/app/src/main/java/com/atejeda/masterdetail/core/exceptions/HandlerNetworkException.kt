package com.atejeda.masterdetail.core.exceptions

import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun handleNetworkExceptions(ex: Exception): String {
    return when (ex) {
        is IOException -> "Error de conexión, verifíque e intente nuevamente (E1)"
        is UnknownHostException -> "Error de conexión, verifíque e intente nuevamente (E2)"
        is HttpException -> apiErrorFromCodeException(ex.code())
        is SocketTimeoutException -> "Error de conexión, verifíque e intente nuevamente (E3)"
        is ConnectException -> "Error de conexión, verifíque e intente nuevamente (E4)"
        else -> "!Ocurrió un error desconocido, por favor intente más tarde..."
    }
}

private fun apiErrorFromCodeException(code: Int): String {
    return when(code){
        502 -> {
            "Internal error! $code"
        }
        401 -> {
            "authentication error! $code"
        }
        else -> "!!Ocurrió un error desconocido, por favor intente más tarde"
    }
}
