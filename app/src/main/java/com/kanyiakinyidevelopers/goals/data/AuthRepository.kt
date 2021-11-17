package com.kanyiakinyidevelopers.goals.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kanyiakinyidevelopers.goals.models.User
import com.kanyiakinyidevelopers.goals.utils.Resource
import com.kanyiakinyidevelopers.goals.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    private val firebaseAuth=FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users")

    //Register
    suspend fun registerUsers(name:String,phone:String,email:String,password:String): Resource<AuthResult> {
        return withContext(Dispatchers.IO){
            safeCall {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid!!
                val user = User(name, phone, email)
                databaseReference.child(uid).setValue(user).await()
                Resource.Success(result)
            }
        }
    }
    //Login
    suspend fun loginUser(email: String,password: String):Resource<AuthResult>{
        return withContext(Dispatchers.IO){
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result)
            }
        }
    }
    //forgot password
    suspend fun forgotPassword(email: String):Resource<Any>{
        return withContext(Dispatchers.IO){
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success(Any())
        }
    }


}