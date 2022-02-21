package com.example.posapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.posapp.databinding.ActivityLoginBinding
import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class LoginActivity : AppCompatActivity() {
    private val TAG= "LoginActivity"
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val secretText = findViewById<TextView>(R.id.TitleTextView)
        secretText.setOnLongClickListener {
            val intent = Intent(this,PosSettings::class.java)
            startActivity(intent)
            true
        }

        val sharedPref=getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        //get the value from credential_stored_key --> true = there are values stored in the shared preference, false = no value stored
        val isAnyCredentialsSaved = sharedPref.getBoolean(getString(R.string.credential_stored_key),false) // the false in the parameter is the default value, in case it cannot find anything
        if(isAnyCredentialsSaved){ // if isAnyCredentialSaved = true (if anything is stored in the shared preference
            val username = sharedPref.getString(getString(R.string.username_key),"")
            val password = sharedPref.getString(getString(R.string.password_key),"")
            //put the username and password automatically for users
            binding.inputUser.setText(username)
            binding.inputPassword.setText(password)
            binding.RememberMeCheckBox?.isChecked =true
        }


    }


    fun clickLogin (view: View) {
        val username = findViewById<EditText>(R.id.input_user)
        Toast.makeText(this, "${username.text} just clicked me ", Toast.LENGTH_SHORT).show()
        if (binding.RememberMeCheckBox?.isChecked == true) {
            val username = binding.inputUser.text.toString()
            val password = binding.inputPassword.text.toString()
            if (username.isNotEmpty()) {
                val sharedPref = getSharedPreferences(
                    getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
                )
                //put data into the shared preference
                with(sharedPref.edit()) {
                    putString(getString(R.string.username_key), username)
                    putString(getString(R.string.password_key), password) //this is not good store plain password, should do encryption first or just don't store password at all
                    putBoolean(getString(R.string.credential_stored_key), true)
                    apply() // apply() is asynchronous (faster than commit()), and it doesnt return boolean indicating success or failure
                }
            }
        } else { // if the user didnt tick "remember me", then just save the 'credential stored key' to false, so that the program stores nothing
            val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.credential_stored_key), false)
                apply()
            }
        }
        val intent = Intent(this, CommandCenterActivity::class.java)
        startActivity(intent)
    }
}