package com.example.rickandmortyapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.CharacterList
import com.example.rickandmortyapp.util.APP_TITLE
import com.example.rickandmortyapp.util.DEFAULT_VALUE
import com.example.rickandmortyapp.util.formatDate

@Composable
fun CharacterDetailsScreen(
    state: CharacterList,
    onRickMortyEvent: (RickMortyEvent) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        BgImage()
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(20.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.rickmorty_back_btn),
                    modifier = Modifier.clickable {
                        onRickMortyEvent(RickMortyEvent.NavigateBack)
                    }
                        .size(60.dp)
                        .padding(start = 10.dp, top = 2.dp)
                        .clickable { },
                    contentDescription = null,
                )
                AsyncImage(
                    model = APP_TITLE,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 30.dp)
                        .fillMaxWidth()
                        .size(100.dp)
                )
            }
            CharacterAvatar(image = state.image, size = 300.dp)
            Spacer(Modifier.height(20.dp))
            Text(
                text = "${state.name?.ifEmpty { DEFAULT_VALUE }}",
                color = Color.Green,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            Text(text = "STATUS - ${state.status?.ifEmpty { DEFAULT_VALUE }}", color = Color.Green)
            Text(
                text = "SPECIES - ${state.species?.ifEmpty { DEFAULT_VALUE }}",
                color = Color.Green
            )
            Text(text = "GENDER - ${state.gender?.ifEmpty { DEFAULT_VALUE }}", color = Color.Green)
            Text(
                text = "LOCATION - ${state.location.name.ifEmpty { DEFAULT_VALUE }}",
                color = Color.Green
            )
            Text(
                text = "ORIGIN - ${state.origin?.name?.ifEmpty { DEFAULT_VALUE }}",
                color = Color.Green
            )
            Text(text = "TYPE - ${state.type?.ifEmpty { DEFAULT_VALUE }}", color = Color.Green)
            Text(text = "CREATED - ${formatDate(state.created)}", color = Color.Green)
        }
    }
}