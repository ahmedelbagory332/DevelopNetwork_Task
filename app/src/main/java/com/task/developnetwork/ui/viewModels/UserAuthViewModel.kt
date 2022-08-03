package com.task.developnetwork.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.developnetwork.data.User
import com.task.developnetwork.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {



    fun signUp(user:User) {
        userRepository.signUp(user)
    }

     suspend fun signIn(userPhone: String): String = userRepository.signIn(userPhone)


    fun getUserStatus(userEmail: String): LiveData<String> {
         return userRepository.getUserStatus(userEmail)
    }

    fun updateUserStatus(userEmail: String,userStatus: String) {
        userRepository.updateUserStatus(userEmail,userStatus)
    }
}