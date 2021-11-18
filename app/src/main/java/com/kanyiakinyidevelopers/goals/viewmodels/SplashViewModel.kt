package com.kanyiakinyidevelopers.goals.viewmodels

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel : ViewModel() {
    var value:  MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    fun setValue(){
        Handler().postDelayed({
            value.value = FirebaseAuth.getInstance().currentUser == null
        },3000)
    }
}