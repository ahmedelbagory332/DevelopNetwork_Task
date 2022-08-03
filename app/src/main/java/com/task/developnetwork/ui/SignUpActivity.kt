package com.task.developnetwork.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.task.developnetwork.R
import com.task.developnetwork.data.User
import com.task.developnetwork.data.UserSharedEmail
import com.task.developnetwork.ui.viewModels.UserAuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit  var fullName: EditText
    private lateinit  var phone:EditText
    private lateinit  var email:EditText
    private lateinit  var password:EditText
    private lateinit  var signUp: Button
    private lateinit  var signIn: Button
    @Inject
     lateinit  var userSharedEmail: UserSharedEmail
    private val userAuthViewModel: UserAuthViewModel by  viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.title = "Sign Up"

        fullName=findViewById(R.id.editTextFullName)
        phone=findViewById(R.id.editTextPhone)
        email=findViewById(R.id.editTextEmail)
        password=findViewById(R.id.editTextPassword)
        signUp=findViewById(R.id.buttonSignUp)
        signIn=findViewById(R.id.buttonSignIn)


        userAuthViewModel.getUserStatus(userSharedEmail.getUserMail()).observe(this, Observer {
            if ( it== "true") {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })

         signIn.setOnClickListener {
             startActivity(Intent(this,SignInActivity::class.java))
             finish()
         }

        signUp.setOnClickListener {
            when {
                fullName.text.toString().trim().isEmpty() -> {
                    Toast.makeText(this, "check your data", Toast.LENGTH_SHORT).show()
                }
                phone.text.toString().trim().length<11 -> {
                    Toast.makeText(this, "phone should consist of minimum 11 number", Toast.LENGTH_SHORT).show()
                }
                email.text.toString().trim().isEmpty() -> {
                    Toast.makeText(this, "check your data", Toast.LENGTH_SHORT).show()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email.text.toString().trim()).matches() -> {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                }
                password.text.toString().trim().length<8 -> {
                    Toast.makeText(this, "Password should consist of minimum 8 characters", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    userAuthViewModel.signUp(
                        User(null,
                        fullName.text.toString().trim(),
                        phone.text.toString().trim(),
                        email.text.toString().trim(),
                        password.text.toString().trim(),
                        "true",
                        fullName.text.toString().trim()+phone.text.toString().trim()
                        )
                    )

                }
        }

    }

}

}