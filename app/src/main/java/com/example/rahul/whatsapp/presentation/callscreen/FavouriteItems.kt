package com.example.rahul.whatsapp.presentation.callscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rahul.whatsapp.R


@Composable
fun FavouriteItems(favouriteContact: favouriteContact) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(
            start = 4.dp, top = 4.dp, end = 12.dp
        )
    ) {
        Image(
            painter = painterResource(favouriteContact.image),
            null,
            modifier = Modifier
                .size(60.dp)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(4.dp))
        Text(
            text = favouriteContact.name,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
