package com.omninos.kotlinproject.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omninos.kotlinproject.data.repository.ApiRepository

class ViewModelFactory(
    private val apiRepository: ApiRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(apiRepository) as T
    }
}