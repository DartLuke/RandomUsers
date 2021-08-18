package com.danielpasser.randomusers.ui.userListFragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielpasser.randomusers.R
import com.danielpasser.randomusers.SpaceItemDecoration
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.ui.adapter.UserAdapter
import com.danielpasser.randomusers.utils.DataState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserListFragment : Fragment(), UserAdapter.OnClickListener {
    private val viewModel: UserListViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val userAdapter = UserAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finishOnBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupAdapter(view)
        setupUI(view)
        if (savedInstanceState == null) viewModel.getUsers(true)
    }

    private fun setupUI(view: View) {
        progressBar = view.findViewById(R.id.progressBar)

    }


    private fun setupAdapter(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.users_recyclerview)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(SpaceItemDecoration())
            adapter = userAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.getUsers(false)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<User>> -> {
                    changeListViewData(dataState.data)
                    showRecycleView(true)
                    showProgressBar(false)
                }
                is DataState.Error -> {
                    showProgressBar(false)
                    showRecycleView(true)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    showRecycleView(false)
                    showProgressBar(true)
                }
            }

        })
    }

    private fun displayError(message: String?) {
        if (message == null)
            Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    private fun showProgressBar(isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE

    }

    private fun showRecycleView(isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun changeListViewData(users: List<User>) {
        userAdapter.changeData(users)
    }

    override fun onClickItem(user: User) {
         switchFragment(user)
    }

    override fun onLongClickItem(user: User) {
        sendEmail(user)
    }

    private fun switchFragment(user: User) {
        val nav = UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(user)
        findNavController().navigate(nav)

    }

    private fun finishOnBackPressed() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAndRemoveTask()
                    System.exit(0)
                }
            })
    }

    private fun sendEmail(user: User) {
        val intent = Intent(Intent.ACTION_SEND)

        intent.apply {
            data = Uri.parse("mailto:")
            type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
        }



        try {
            startActivity(Intent.createChooser(intent, "Send mail..."))

            Log.v("Test", "Finished sending email...")
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "There is no email client installed.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}