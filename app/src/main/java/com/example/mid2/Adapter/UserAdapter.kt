package com.example.mid2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mid2.Model.UserEntity
import com.example.mid2.R

/**
 * Adapter for User Recycler view
 */
class UserAdapter(): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var users: List<UserEntity> = emptyList()

    fun setUsers(users: List<UserEntity>) {
        this.users = users
        notifyDataSetChanged()
    }

    // view holder for user name and first letter in user_item
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textViewFirstLetter: TextView

        init {
            textView = view.findViewById(R.id.user_name_text)
            textViewFirstLetter = view.findViewById(R.id.user_first_letter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = users[position].name
        holder.textViewFirstLetter.text = users[position].name[0].toString()
    }


}