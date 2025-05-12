package com.example.codedoc


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import create_client

val client = create_client()
internal const val OK_STATUS = 2000

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets}

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPassword: EditText = findViewById(R.id.user_password)
        val userEmail: EditText = findViewById(R.id.user_email)
        val button: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)


        client.responseListener = { status, message ->
            runOnUiThread {
                if (status == OK_STATUS) {
                    Toast.makeText(
                        this@MainActivity,
                        "Пользователь ${userLogin.text} добавлен",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this@MainActivity, mainFunction::class.java)
                    startActivity(intent)
                    finish()
                    client.disconnect()
                    userLogin.text.clear()
                    userPassword.text.clear()
                    userEmail.text.clear()

                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "$message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        linkToAuth.setOnClickListener{
            val intent = Intent(this, authorizationActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener{
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()
            val email = userEmail.text.toString().trim()
            client.sendUserData(email, login, password)
        }
    }
}
