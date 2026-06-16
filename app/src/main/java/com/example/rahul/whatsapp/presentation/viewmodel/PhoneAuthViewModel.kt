package com.example.rahul.whatsapp.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap


import android.util.Log

import androidx.lifecycle.ViewModel
import com.example.rahul.whatsapp.models.PhoneAuthUser
import com.google.firebase.FirebaseException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {
    // ye  uska code hai ki kaise hmare phne par otp ayega

    private val _authstate = MutableStateFlow<AuthState>(AuthState.Ideal) // ye private hoga
    val authState = _authstate.asStateFlow()// ye ui par dekhega

    // yha ussera detail store hoga node mai
    private val userRef = database.reference.child("users")

    fun sendcerificationCode(phoneNumber: String,activity: Activity){
        _authstate.value= AuthState.Loading


        val option = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                Log.d("PhoneAuth","On codesent triggered,verification Id: $id")
                _authstate.value = AuthState.CodeSent(id)
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signinWithCredential(credential,context= activity)
            }

            override fun onVerificationFailed(exception:  FirebaseException) {

                Log.e("PhoneAuth","Verification failed : ${exception.message}" )
                _authstate.value= AuthState.Error(exception.message?:"Verifiaction failed")

            }


        }

        val phoneAuthOption = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(option)
            .build()
        PhoneAuthProvider.verifyPhoneNumber((phoneAuthOption))

    }

    private  fun signinWithCredential(credential: PhoneAuthCredential,context: Context){
        _authstate.value  = AuthState.Loading  // ye wait stage mai raktha hai

        // verifiacation process
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {  task->


                if(task.isSuccessful){
                    val user = firebaseAuth.currentUser
                    val phoneAuthUser = PhoneAuthUser(
                        userId = user?.uid?:"",
                        phoneNumber  = user?.phoneNumber?:""
                    )

                    markUserAsSignedIn(context)
                    _authstate.value = AuthState.Success(phoneAuthUser)

                    fetchUserProfile(user?.uid?:"")

                }else{
                    _authstate.value = AuthState.Error(task.exception?.message?:"sign in failed")
                }

            }
    }

    // this funtion use for check login is done or not
    private fun markUserAsSignedIn(context: Context) {
        val sharedPreference =
            context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

        sharedPreference.edit()
            .putBoolean("isSignedIn", true)
            .apply()
    }

    private  fun fetchUserProfile(userId: String){
        val useRef = userRef.child(userId)
        useRef.get().addOnSuccessListener { snapshot ->
            if(snapshot.exists()){
                val userProfile = snapshot.getValue(PhoneAuthUser::class.java)
                if(userProfile !=null){
                    _authstate.value = AuthState.Success(userProfile)
                }
            }
        }.addOnFailureListener {
            _authstate.value = AuthState.Error("Failed to fetch the user Profile")
        }

    }


    fun verifycode(otp: String,context: Context){

        val currentAuthState= _authstate.value


        if(currentAuthState!is AuthState.CodeSent|| currentAuthState.verificationId.isEmpty()){

            Log.e("PhoneAuth","Attempting to verify OTP without a valid verification ID")

            _authstate.value= AuthState.Error("verification not started or invalid ID")
            return
        }

        val  credential= PhoneAuthProvider.getCredential(currentAuthState.verificationId,otp)
        signinWithCredential(credential,context)
    }

    fun saveUserProfile(userId: String, name: String, status: String, profileImage: Bitmap?) {
        val database = FirebaseDatabase.getInstance().reference

        val encodedImage = if (profileImage != null) {
            convertBitmapToBase64(profileImage)
        } else {
            ""
        }

        val userProfile = PhoneAuthUser(
            userId = userId,
            name = name,
            status = status,
            phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: "",
            profileImage = encodedImage
        )

        database.child("users").child(userId).setValue(userProfile)
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String{   // change image to the bitmap to store image
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(  // base 64  used to convert binary data to text data
            byteArray,
            Base64.DEFAULT
        )
    }

    fun resetAuthState(){
        _authstate.value = AuthState.Ideal
    }

    fun signOut(activity: Activity){
        firebaseAuth.signOut()
        val sharedPreferences= activity.getSharedPreferences("app_prefs", Activity.MODE_PRIVATE)
      sharedPreferences.edit().putBoolean("isSigned",false).apply()
    }





}

sealed class  AuthState{
    object Ideal: AuthState()
    object Loading: AuthState()
    data class  CodeSent(val verificationId : String): AuthState()
    data class  Success(val user: PhoneAuthUser): AuthState()
    data class  Error(val message: String): AuthState()


}