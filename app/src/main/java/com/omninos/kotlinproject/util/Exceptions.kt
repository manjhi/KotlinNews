package com.omninos.kotlinproject.util

import java.io.IOException

class ApiException(message:String):IOException(message)
class NoInternetException(mesage: String) : IOException(mesage)