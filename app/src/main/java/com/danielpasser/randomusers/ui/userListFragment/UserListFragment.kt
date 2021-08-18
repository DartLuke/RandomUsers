package com.danielpasser.randomusers.ui.userListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielpasser.randomusers.R
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.ui.adapter.UserAdapter
import com.danielpasser.randomusers.utils.DataState


class UserListFragment : Fragment(), UserAdapter.OnClickListener {
    private lateinit var viewModel: UserListViewModel
    private lateinit var progressBar: ProgressBar
    private val userAdapter = UserAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
        setupAdapter(view)
        setupUI(view)
        viewModel.getUsers()
    }


    private fun setupUI(view: View) {
        progressBar = view.findViewById(R.id.progressBar)

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
    }

    private fun setupAdapter(view: View) {
        view.findViewById<RecyclerView>(R.id.users_recyclerview).apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = userAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getUsers()
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
                    showProgressBar(false)
                }
                is DataState.Error -> {
                    showProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
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

    private fun changeListViewData(users: List<User>) {
        userAdapter.changeData(users)
    }

    override fun onClickItem(user: User) {
        switchFragment(user)

    }

    private fun switchFragment(user: User) {
        val nav = UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(user)
        findNavController().navigate(nav)

    }
}