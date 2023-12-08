package com.example.mid2.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mid2.Database.UserDatabase

class MainViewModelFactory(private val userDB: UserDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(this.userDB) as T
        }
        return super.create(modelClass)
    }

}