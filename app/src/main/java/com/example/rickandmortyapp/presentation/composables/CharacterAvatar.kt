package com.example.rickandmortyapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.rickandmortyapp.R

@Composable
fun CharacterAvatar(image: String, size: Dp? = null) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = image)
                .apply {
                    error(drawableResId = R.drawable.rickmortyapp_default_avatar)
                    placeholder(drawableResId = R.drawable.rickmortyapp_default_avatar)
                    crossfade(true)
                }
                .build(),
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = size?.let { Modifier.size(it) }
            ?: Modifier,
    )
}
