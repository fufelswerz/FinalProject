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

class authorizationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authorization)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPassword: EditText = findViewById(R.id.user_password_auth)
        val button: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        client.responseListener = { status, message ->
            runOnUiThread {
                if (status == OK_STATUS) {
                    Toast.makeText(
                        this@authorizationActivity,
                        "Пользователь ${userLogin.text} вошел в систему",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this@authorizationActivity, mainFunction::class.java)
                    startActivity(intent)
                    finish()
                    client.disconnect()
                    userLogin.text.clear()
                    userPassword.text.clear()
                } else {
                    Toast.makeText(
                        this@authorizationActivity,
                        "Ошибка: $message",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }

        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()
            client.sendAuthorizationData(login, password)
        }
    }
}
