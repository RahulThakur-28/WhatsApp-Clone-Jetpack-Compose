package com.example.rahul.whatsapp.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rahul.whatsapp.presentation.callscreen.CallScreen
import com.example.rahul.whatsapp.presentation.communitesscreen.CommunitiesScreen
import com.example.rahul.whatsapp.presentation.homescreen.HomeScreen
import com.example.rahul.whatsapp.presentation.profile.UserProfileSetScreen
import com.example.rahul.whatsapp.presentation.splashscreen.splashScreen
import com.example.rahul.whatsapp.presentation.updatescreen.UpdateScreen
import com.example.rahul.whatsapp.presentation.userregestrationscreen.AuthScreen
import com.example.rahul.whatsapp.presentation.viewmodel.BaseViewModel
import com.example.rahul.whatsapp.presentation.welcomescreen.WelcomeScreen


@Composable
fun WhatsAppNaviagtionSystem() {

    val navController = rememberNavController()


    NavHost(
        startDestination = Routes.SplashScreen,
        navController = navController
        // ye batat hai ki komsi screenn se konsi screen mai jana hai
        // start destination btata hai ki  hmari konsi screen se kha jana

    )

    {

        composable<Routes.SplashScreen> {
            splashScreen(navController)
        }
        // navgraph helps to navigate konse route mai hme konsi screen maimjana  hai


        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }
        composable<Routes.UserRegisterationScreen> {
            AuthScreen(navController)
        }
        composable<Routes.HomeScreen> {
            val baseViewModel : BaseViewModel= hiltViewModel()
            HomeScreen(navController,baseViewModel)
        }
        composable<Routes.UpdateScreen> {
            UpdateScreen(navController)
        }
        composable<Routes.CommunitiesScreen> {

            CommunitiesScreen(navController)
        }
        composable<Routes.CallScreen> {
            CallScreen(navController)
        }
        composable<Routes.UserProfileSetScreen> {
            UserProfileSetScreen(navHostController = navController)
        }
    }
}