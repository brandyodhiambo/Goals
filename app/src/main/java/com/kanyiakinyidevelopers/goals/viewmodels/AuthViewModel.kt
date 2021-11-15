package com.kanyiakinyidevelopers.goals.viewmodels

import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.kanyiakinyidevelopers.goals.data.AuthRepository
import com.kanyiakinyidevelopers.goals.utils.Event
import com.kanyiakinyidevelopers.goals.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class   AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    //Register
    private val _registerStatus=MutableLiveData<Event<Resource<AuthResult>>>()
    val registerStatus:LiveData<Event<Resource<AuthResult>>> = _registerStatus

    //login
    private val _loginStatus =MutableLiveData<Event<Resource<AuthResult>>>()
    val loginStatus:LiveData<Event<Resource<AuthResult>>> = _loginStatus


    fun registerUsers(name:String,phone:String,email:String,password:String){
        var error = if (name.isEmpty()||phone.isEmpty()||email.isEmpty()||password.isEmpty())
        {
            "Empty Strings"
        } else if (phone.length!=10){
            "Not valid phone number"
        }
        else if (password.length<8){
            "Password too short"
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            "Email not valid"
        }
        else null

        error?.let {
            _registerStatus.postValue(Event(Resource.Error(it)))
        }

        _registerStatus.postValue(Event(Resource.Loading()))

        viewModelScope.launch(Dispatchers.Main){
            val result  = authRepository.registerUsers(name, phone, email, password)
            _registerStatus.postValue(Event(result))
        }
    }
    fun loginUser(email:String,password: String){
        var error = if(email.isEmpty()||password.isEmpty()){
            "Empty Strings"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            "Email not valid"
        }
        else if (password.length<8){
            "password too short"
        }
        else null

        error?.let {
            _loginStatus.postValue(Event(Resource.Error(it)))
        }
        _loginStatus.postValue(Event(Resource.Loading()))

        viewModelScope.launch(Dispatchers.Main){
            val result = authRepository.loginUser(email, password)
            _loginStatus.postValue(Event(result))
        }
    }
}