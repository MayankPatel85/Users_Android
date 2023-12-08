package com.example.mid2.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mid2.Api.ApiClient
import com.example.mid2.Model.UserEntity
import com.example.mid2.Database.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for MainActivity
 */
class MainViewModel(private val userDB: UserDatabase): ViewModel() {

    val usersList = MutableLiveData<List<UserEntity>>()
    val isLoading = MutableLiveData<Boolean>()
    val isErrorOccurred = MutableLiveData<Boolean>()
    var job: Job? = null

    /**
     * get the users data from API
     * caches that in database
     * notifies changes to MainActivity to update views
     */
    fun getAllUsers() {
        // setting isLoading to true for showing progress bar
        isLoading.value = true
        // creating api service
        val apiClient = ApiClient.apiService
        // creating job to run on IO thread
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                // fetching response from api
                val response = apiClient.getAllUsers()
                // deleting previous records from database
                userDB.userDao().deleteAll()
                // adding data to database
                response.body()?.let { userDB.userDao().insertUserEntity(it) }
                // updating userList on Main thread for updating the views
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val userList = response.body()
                        usersList.postValue(userList)
                        isLoading.postValue(false)
                    }
                }
            } catch (e: Error) {
                // for displaying error alert dialog
                withContext(Dispatchers.Main) {
                    isErrorOccurred.postValue(true)
                }
            }
        }
    }

    /**
     * fetches all the users from the database
     */
    fun getAllUsersFromDB() {
        // for showing progress bar
        isLoading.value = true
        // performing operations on IO thread
        job = CoroutineScope(Dispatchers.IO).launch {
            // fetching data from database
            val usersListFromDB = userDB.userDao().getAll()
            // updating userList on Main thread to update the view
            withContext(Dispatchers.Main) {
                usersList.postValue(usersListFromDB)
                isLoading.postValue(false)
            }
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

}