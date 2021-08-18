package com.danielpasser.randomusers.ui.userListFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.models.UserResult
import com.danielpasser.randomusers.ui.repository.Repository
import com.danielpasser.randomusers.utils.DataState


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import java.lang.Exception

class UserListViewModel : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<User>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<User>>> get() = _dataState

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            Repository().getUsers().onEach { dateState -> _dataState.value = dateState }
                .launchIn(viewModelScope)

        }
    }
}