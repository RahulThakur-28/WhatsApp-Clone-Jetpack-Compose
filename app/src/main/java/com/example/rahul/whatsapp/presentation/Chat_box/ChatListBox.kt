package com.example.rahul.whatsapp.presentation.Chat_box

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rahul.whatsapp.R
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import com.example.rahul.whatsapp.presentation.viewmodel.BaseViewModel

@Composable
fun ChatListBox(
    chatListModel: ChatListModel,
    onClick: () -> Unit,
    baseViewModel: BaseViewModel
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val profileImage = chatListModel.profileImage

        val bitmap = remember(profileImage) {
            profileImage?.let {
                baseViewModel.base64ToBitmap(it)
            }
        }

        Image(
            painter =
                if (bitmap != null) {
                    BitmapPainter(bitmap.asImageBitmap())
                } else {
                    painterResource(R.drawable.uer_profile_prototupe)
                },
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = chatListModel.name ?: "Unknown",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = chatListModel.time ?: "--:--",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = chatListModel.message ?: "message",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}