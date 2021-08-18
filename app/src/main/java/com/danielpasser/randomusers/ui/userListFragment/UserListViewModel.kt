package com.danielpasser.randomusers.ui.userListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielpasser.randomusers.models.User
import com.danielpasser.randomusers.repository.Repository
import com.danielpasser.randomusers.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repository: Repository)

    : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<User>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<User>>> get() = _dataState

    fun getUsers(getFromDb:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers(getFromDb).onEach { dateState -> _dataState.value = dateState }
                .launchIn(viewModelScope)

        }
    }
}