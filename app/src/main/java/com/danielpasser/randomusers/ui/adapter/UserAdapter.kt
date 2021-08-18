package com.danielpasser.randomusers.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danielpasser.randomusers.R
import com.danielpasser.randomusers.models.User

class UserAdapter(private var onClickListener: OnClickListener) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private val users: ArrayList<User> = arrayListOf()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, onClickListener: OnClickListener) {
            itemView.apply {
                itemView.setOnClickListener { onClickListener.onClickItem(user) }

                findViewById<TextView>(R.id.item_view_text_view_name).text =
                    user.name?.first + " " + user.name?.last

                findViewById<TextView>(R.id.item_view_text_view_email).text = user.email

                Glide.with(context).load(user.picture?.thumbnail)
                    .into(findViewById<ImageView>(R.id.item_view_image_view));
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
    )


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(users[position], onClickListener)
    }

    override fun getItemCount() = users.size
    fun changeData(_users: List<User>) {
        users.apply {
            clear()
            addAll(_users)
        }
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClickItem(user: User)

    }

}