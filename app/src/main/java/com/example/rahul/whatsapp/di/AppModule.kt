package com.example.rahul.whatsapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton

    fun provideFirebaseAuth(): FirebaseAuth {  // this is for the firebase quthentication

        return FirebaseAuth.getInstance()

    }

    @Provides
    @Singleton
    // ye firebase ka instance provide kargea ye hi iska mainn kaam jai

    fun  provideFirebaseDatabase(): FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }


}