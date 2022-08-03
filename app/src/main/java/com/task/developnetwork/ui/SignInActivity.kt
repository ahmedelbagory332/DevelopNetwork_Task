package com.task.developnetwork.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.task.developnetwork.R
import com.task.developnetwork.data.UserSharedEmail
import com.task.developnetwork.ui.viewModels.UserAuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit  var phone:EditText
    private lateinit  var password:EditText
    private lateinit  var signUp: Button
    private lateinit  var signIn: Button
    @Inject
     lateinit  var userSharedEmail: UserSharedEmail
    private val userAuthViewModel: UserAuthViewModel by  viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar!!.title = "Sign In"

        phone=findViewById(R.id.editTextPhone)
        password=findViewById(R.id.editTextPassword)
        signUp=findViewById(R.id.buttonSignUp)
        signIn=findViewById(R.id.buttonLogin)





        signUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        signIn.setOnClickListener {
            when {

                phone.text.toString().trim().length<11 -> {
                    Toast.makeText(this, "phone should consist of minimum 11 number", Toast.LENGTH_SHORT).show()
                }
                password.text.toString().trim().length<8 -> {
                    Toast.makeText(this, "Password should consist of minimum 8 characters", Toast.LENGTH_SHORT).show()
                }

                else -> {

                    runBlocking {

                       if (userAuthViewModel.signIn(phone.text.toString().trim())==password.text.toString().trim()){
                           Toast.makeText(applicationContext, "Signed in Success", Toast.LENGTH_SHORT).show()
                               userAuthViewModel.updateUserStatus(userSharedEmail.getUserMail(),"true")
                               startActivity(Intent(applicationContext,SignUpActivity::class.java))
                               finish()
                       }else{
                                Toast.makeText(applicationContext, "Check Your Info", Toast.LENGTH_SHORT).show()
                            }
                    }

                }
            }

        }






}

}