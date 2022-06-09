package com.example.todoapplication.view.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.todoapplication.databinding.ActivityLoginBinding

import com.example.todoapplication.view.HomeActivity
import com.example.todoapplication.viewmodel.LoginViewModel
import com.example.todoapplication.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding //TODO: class name should contain <layout-file-name + Binding at end>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)//TODO: root refers the root View in our layout file activity_login.xml

//TODO: rename the view id by suffixing the component name . ex: username_edittext, login_button, progress_bar
        val usernameField = binding.username
        val passwordField = binding.password
        val loginButton = binding.login
        val progressBar = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())//TODO: current activity acts as lifecycle owner
                .get(LoginViewModel::class.java)
        //TODO:case 1: when fields are populated with default values
        if (loginButton.text.toString().isNotEmpty() && passwordField.text.toString().isNotEmpty()) {
            loginViewModel.loginDataChanged(loginButton.text.toString(), passwordField.text.toString())
        }
//TODO: register observer to observe on the live data fields Login Form field
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer { //TODO: onChanged() lambda is called, it -> LoginFormState
            val loginState = it ?: return@Observer
            //TODO: disable login button unless both username / password is valid
            loginButton.isEnabled = loginState.isDataValid

           //TODO: enable error
            if (loginState.usernameError != null) {
                usernameField.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                passwordField.error = getString(loginState.passwordError)
            }
        })
          //TODO:Observe  Login response
        loginViewModel.loginState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer //TODO: what this return do? check later

            progressBar.visibility = View.GONE
            if (loginState.error != null) {
                showLoginFailed(loginState.error)
            }
            if (loginState.success != null) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        })
/*TODO: submit form on below scenarios
        1. on clicking login button
        2. on clicking on Done Action key on keyboard
        */
        usernameField.afterTextChanged {
            loginViewModel.loginDataChanged(
                    usernameField.text.toString(),
                    passwordField.text.toString()
            )
        }

        passwordField.apply {
            //Lambda that gets called when data changed in Fields
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        usernameField.text.toString(),
                        passwordField.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->//other 2 params are 1.TextView, 3.KeyEvent
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> //TODO: when Done action key (right mark) is clicked on key board
                        loginViewModel.login(usernameField.text.toString(), passwordField.text.toString()
                        )
                }
                false //TODO: return false to indicate that we haven't consumed this event and let other listener act for it.
            }

            loginButton.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                loginViewModel.login(usernameField.text.toString(), passwordField.text.toString())
            }
        }
    }
//TODO: show toast for error
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChangeCallback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChangeCallback.invoke(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}