package com.task.developnetwork.data
import android.app.Application
import android.util.Log
import android.widget.Toast
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.Exception

class UserRepository @Inject constructor(private val userSharedEmail: UserSharedEmail,private val db: UserDataBase, private val ctx: Application) {

    private val _userStatus = MutableStateFlow("")


    fun signUp(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.userDao().insertRestaurants(user)
                userSharedEmail.saveUserMail(user.email)
                withContext(Dispatchers.Main) {
                    Toast.makeText(ctx, "Signed up Success, Please login", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Log.d("UserRepository", e.message.toString())
            }

        }

    }

     suspend fun signIn(userPhone: String): String =
         runBlocking {
             async { db.userDao().getUserPassword(userPhone) }
         }.await()



    fun getUserStatus(userEmail: String): LiveData<String> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _userStatus.emit(db.userDao().getUserStatus(userEmail))
            }
            catch (e:Exception){
                Log.d("UserRepository", e.message.toString())
            }
        }
        return  _userStatus.asLiveData()
    }

    fun updateUserStatus(userEmail: String,userStatus: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                db.userDao().updateUserStatus(userEmail,userStatus)
            }
            catch (e:Exception){
                Log.d("UserRepository", e.message.toString())
            }
        }
    }

}


