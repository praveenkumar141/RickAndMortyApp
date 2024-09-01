package com.example.rickandmortyapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.CharacterListResponse
import com.example.rickandmortyapp.util.APP_TITLE
import com.example.rickandmortyapp.util.BG_IMAGE

@Composable
fun RickMortyParentUI(
    state: CharacterListResponse,
    onRickMortyEvent: (RickMortyEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.Black)
    ) {
        BgImage()
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp), verticalArrangement = Arrangement.Center
        ) {
            state.results?.let {
                AsyncImage(
                    model = APP_TITLE,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                )

                val listState = rememberLazyListState()

                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    state = listState
                ) {
                    itemsIndexed(state.results) { index, item ->
                        Row(Modifier.clickable { onRickMortyEvent(RickMortyEvent.CharacterClicked(id = item.id)) }) {
                            CharacterAvatar(item.image)
                            Spacer(modifier = Modifier.width(20.dp))
                            Column {
                                Text(
                                    text = "${item.name}",
                                    color = Color.Green,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "SPECIES - ${item.species}", color = Color.Green)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "STATUS - ${item.status}", color = Color.Green)
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    if (state.info?.next != null) {
                        item {
                            LoadingItem()
                        }
                    }
                }
                if (state.info?.next != null && listState.isScrolledToEnd()) {
                    state.info.next.toString().substringAfter("page=").toIntOrNull()
                        ?.let { it1 -> RickMortyEvent.LoadNextPage(it1) }
                        ?.let { it2 -> onRickMortyEvent(it2) }
                }
            }
        }
    }
}

fun LazyListState.isScrolledToEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}

@Composable
fun LoadingItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color.Green)
    }
}

@Composable
fun BgImage() {
    AsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .alpha(0.5f),
        model = ImageRequest.Builder(LocalContext.current)
            .data(BG_IMAGE)
            .build(),
        contentDescription = null
    )
}

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

