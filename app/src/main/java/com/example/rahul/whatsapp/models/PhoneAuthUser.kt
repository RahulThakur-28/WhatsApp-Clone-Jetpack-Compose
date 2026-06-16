package com.example.rahul.whatsapp.models

import android.media.Image

data class PhoneAuthUser(
    val userId: String ="",
    val phoneNumber:String ="",
    val name : String ="",
    val status : String ="",
    val profileImage:String? =null
)
