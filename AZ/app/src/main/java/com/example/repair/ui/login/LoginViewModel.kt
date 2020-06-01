package com.example.repair.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.repair.data.LoginRepository
import com.example.repair.data.Result

import com.example.repair.R
import com.example.repair.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun clear() {
        _loginResult.value = null
    }

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("TAG", "1.执行task1.... [当前线程为：${Thread.currentThread().name}]")
            val result = withContext(Dispatchers.IO) {
                Log.e("TAG", "1.执行task1.... [当前线程为：${Thread.currentThread().name}]")
                loginRepository.login(username, password)
            }
            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = result.data)
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }


    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.length == 11
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 7
    }
}
