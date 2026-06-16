package com.example.rahul.whatsapp.presentation.callscreen

import android.R.attr.text
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rahul.whatsapp.R

@Composable

fun FavouriteScreen() {

    val sampleFavourites = listOf(
        favouriteContact(R.drawable.person_22, "Vikash"),
        favouriteContact(R.drawable.person_23, "Brother"),
        favouriteContact(R.drawable.person_24, "Aman Yadav Freind"),
        favouriteContact(R.drawable.gir, "Riya office"),
        favouriteContact(R.drawable.girl_3, "Animka")

    )




    Column(modifier = Modifier.padding(bottom = 8.dp, start = 6.dp)) {
        Text(
            text = "Favourites",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Row(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {

            sampleFavourites.forEach {
                FavouriteItems(it)

            }

        }

    }
}

data class favouriteContact(val image: Int, val name: String)