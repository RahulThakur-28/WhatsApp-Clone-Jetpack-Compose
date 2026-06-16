package com.example.rahul.whatsapp.models

import com.google.firebase.Timestamp

data class Message(
    val senderPhoneNumber: String= "",
    val message: String="",
    val timestamp: Long = 0L
)
