package com.example.bitirmeprojesi.ui.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bitirmeprojesi.data.entity.Filmler
import com.example.bitirmeprojesi.ui.viewmodel.DetayViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detay(
    navController: NavController,
    gelenFilm: Filmler,
    detayViewModel: DetayViewModel = viewModel()
) {
    //Film detayları için state tanımlamaları
    val adet by detayViewModel.adet.observeAsState()
    val favoriteFilms by detayViewModel.favoriteFilms.collectAsState()
    val email = detayViewModel.emailAl()

    //Toplam ücret hesaplama
    val toplamUcret = remember(adet) { detayViewModel.toplamUcret(gelenFilm.price) }

    //Başlangıçta adet değerini sıfırlama
    LaunchedEffect(Unit) {
        detayViewModel.adetYapilandir()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff190e36),
                    titleContentColor = Color(0xffb8b7bf),
                    actionIconContentColor = Color(0xffb8b7bf),
                    navigationIconContentColor = Color(0xffb8b7bf)
                ),
                title = {
                    Text(
                        "Detay",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(color = Color(0xff190e36))
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 5.dp)
            ) {

                //Film detayları bölümü
                item {
                    Card(
                        modifier = Modifier
                            .padding(all = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xff2d204b),
                            contentColor = Color(0xffcccbd3))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(all = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                            //Film ismi
                            Text(
                                text = gelenFilm.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                                Text(text = gelenFilm.director, style = MaterialTheme.typography.bodyLarge)

                            Spacer(modifier = Modifier.height(8.dp))

                                Box(modifier = Modifier
                                    .size(200.dp, 300.dp)
                                    .clip(RoundedCornerShape(5.dp))) {
                                    val url = "http://kasimadalan.pe.hu/movies/images/${gelenFilm.image}"
                                    GlideImage(
                                        imageModel = url,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    )
                                    Icon(
                                        Icons.Rounded.Star,
                                        contentDescription = "Favori",
                                        tint = if (favoriteFilms.contains(gelenFilm.id)) Color.Yellow else Color.White,
                                        modifier = Modifier
                                            .size(26.dp)
                                            .align(Alignment.TopStart)
                                            .background(Color(0xffa0a0c2).copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                            .clickable { detayViewModel.toggleFavorite(gelenFilm.id) }
                                            .padding(4.dp)
                                    )
                                    Text(
                                        text = "${gelenFilm.year}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                            .padding(4.dp)
                                    )
                                    Text(
                                        text = "${gelenFilm.rating}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                            .padding(4.dp)
                                    )
                                    Text(
                                        text = "${gelenFilm.category}",
                                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                            .padding(4.dp)
                                    )
                                }

                            Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = gelenFilm.description,
                                    style = TextStyle(textAlign = TextAlign.Center)
                                )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = { detayViewModel.adetAzalt() },
                                    enabled = adet!! > 1,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xffa0a0c2),
                                        contentColor = Color(0xff332d53)),
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    Icon(
                                        Icons.Filled.KeyboardArrowDown,
                                        contentDescription = "",
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = adet.toString(),
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Button(
                                    onClick = { detayViewModel.adetArttir() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xffa0a0c2),
                                        contentColor = Color(0xff332d53)),
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    Icon(
                                        Icons.Filled.KeyboardArrowUp,
                                        contentDescription = "",
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            //Toplam ücret
                            Text(
                                text = "₺${toplamUcret}",
                                fontSize = 23.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            //Sepete ekle butonu
                            Button(
                                onClick = {

                                    if (email != null) {

                                    detayViewModel.sepeteEkle(
                                        name = gelenFilm.name,
                                        image = gelenFilm.image,
                                        price = gelenFilm.price,
                                        orderAmount = adet!!,
                                        userName = email,
                                        category = gelenFilm.category,
                                        rating = gelenFilm.rating,
                                        year = gelenFilm.year,
                                        director = gelenFilm.director,
                                        description = gelenFilm.description
                                    )
                                    navController.navigate("sepet/$email") {
                                        popUpTo("detay") { inclusive = true }
                                    } }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xffa0a0c2),
                                    contentColor = Color(0xff332d53)),
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Text("Sepete Ekle")
                            }
                        }
                    }
                }
            }
        }


    }

    }

}