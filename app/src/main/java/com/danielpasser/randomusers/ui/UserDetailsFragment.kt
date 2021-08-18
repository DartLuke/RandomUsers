package com.danielpasser.randomusers.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.danielpasser.randomusers.R
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.utils.Utils

class UserDetailsFragment : Fragment() {

    private val safeArgs: UserDetailsFragmentArgs by navArgs()
    private val user: User by lazy { safeArgs.user }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.user_details_text_view_name).text =
            user.name?.first + " " + user.name?.last
        view.findViewById<TextView>(R.id.user_details_text_birthday).text =
            Utils.daysUntilBirthDay(user.dob)
        Glide.with(view.context).load(user.picture?.thumbnail)
            .into(view.findViewById<ImageView>(R.id.user_details_image_view));

    }
}