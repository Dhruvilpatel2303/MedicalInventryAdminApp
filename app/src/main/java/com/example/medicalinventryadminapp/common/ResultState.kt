package com.example.medicalinventryadminapp.common

sealed class ResultState<out T> {

  data  class suceess<out T>(val data: T) : ResultState<T>()
   data  class error<T>(val exception: Exception) : ResultState<T>()
    object loading : ResultState<Nothing>()


}