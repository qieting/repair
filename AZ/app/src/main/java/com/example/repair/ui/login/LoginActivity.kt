package com.example.repair.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.example.repair.App
import com.example.repair.MainActivity
import com.example.repair.MyUser

import com.example.repair.R
import com.example.repair.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.activity_login.view.*
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        var messeageBtn = findViewById<Button>(R.id.messagebtn);
        var messageEdt = findViewById<EditText>(R.id.messageEdt);
        messeageBtn.setOnClickListener {
            if (username.text.length == 11) {
                var a = username.text.toString()
                BmobSMS.requestSMSCode(a, "注册", object : QueryListener<Int>() {
                    override fun done(p0: Int?, p1: BmobException?) {
                        if (p1 == null) {
                            messeageBtn.isClickable = false;
                            Toast.makeText(
                                applicationContext,
                                "短信验证码发送成功",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "短信验证码发送失败，${p1.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            } else {
                Toast.makeText(baseContext, "手机号位数错误", Toast.LENGTH_SHORT).show();
            }
        }

        loginViewModel = ViewModelProvider(this.application as App, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                Toast.makeText(applicationContext, "登录失败", Toast.LENGTH_SHORT).show()
            }
            if (loginResult.success != null) {
                updateUiWithUser()
            }
//            setResult(Activity.RESULT_OK)
//
//            //Complete and destroy login activity once successful
//            finish()
        })


        username.afterTextChanged() {

        }

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }


            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                try {
                    loginViewModel.login(username.text.toString(), password.text.toString())
                } catch (e: Exception) {
                    print(e.message)
                }

//                BmobSMS.verifySmsCode(username.text.toString(),messageEdt.text.toString(),object :UpdateListener(){
//                    override fun done(p0: BmobException?) {
//                        if(p0==null){
//                            loginViewModel.login(username.text.toString(), password.text.toString())
//                        }else{
//                            Toast.makeText(
//                                applicationContext,
//                                "短信验证码验证失败，${p0.message}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                            loading.visibility=View.INVISIBLE
//                        }
//                    }
//                })

            }
        }
    }

    private fun updateUiWithUser() {

        MyUser.user = loginViewModel.loginResult.value!!.success!!;

        //  if(MyUser.user.type.equals("在线"))
        var intent: Intent = Intent(baseContext, MainActivity::class.java);
        startActivity(intent)
        finish()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
