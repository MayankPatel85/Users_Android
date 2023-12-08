package com.example.mid2

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mid2.Adapter.UserAdapter
import com.example.mid2.Database.UserDatabase
import com.example.mid2.ViewModel.MainViewModel
import com.example.mid2.ViewModel.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // creating databse named midterm
        val db = Room.databaseBuilder(
            this,
            UserDatabase::class.java, "midterm"
        ).build()

        // creating adapter for recycler view to display users
        adapter = UserAdapter()

        // progress view
        val progressBar = findViewById<ProgressBar>(R.id.main_progress_bar)

        val userRecyclerView = findViewById<RecyclerView>(R.id.user_recycler_view)
        // setting adapter
        userRecyclerView.adapter = adapter

        // creating view model from factory
        viewModel = ViewModelProvider(this, MainViewModelFactory(db))[MainViewModel::class.java]

        // observing to isLoading to display progress bar when getting data from api
        viewModel.isLoading.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        // observing to users list to update recycler view with live data
        viewModel.usersList.observe(this){
            adapter.setUsers(it)
        }

        // checking for internet connection
        // and loading from database if no internet connection
        // otherwise getting data from api
        if (checkForInternetConnection(this)) {
            viewModel.getAllUsers()
        } else {
            viewModel.getAllUsersFromDB()
        }

        // observing for isErrorOccurred and displaying alert dialog if error occurred
        viewModel.isErrorOccurred.observe(this) {
            if (it) {
                showErrorDialog(this, viewModel)
            }
        }

    }

    /**
     * checks for internet connection
     * @param ctx application context
     * @return true if connected to internet otherwise false
     */
    fun checkForInternetConnection(ctx: Context): Boolean {
        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network  = connectivityManager.activeNetwork
        if(network != null) {
            return true
        }
        return false
    }

    /**
     * shows alert dialog
     * @param ctx application context
     * @param viewModel the view model
     */
    fun showErrorDialog(ctx: Context, viewModel: MainViewModel) {
        MaterialAlertDialogBuilder(ctx, com.google.android.material.R.style.MaterialAlertDialog_Material3)
            .setTitle("Error")
            .setMessage("Sorry, an error occurred while getting the users data.")
            .setPositiveButton("OK") { _, _ ->
                viewModel.isErrorOccurred.value = false
            }
            .show()
    }
}