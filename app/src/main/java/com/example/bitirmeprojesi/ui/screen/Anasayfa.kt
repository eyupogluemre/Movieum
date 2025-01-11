package com.example.bitirmeprojesi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.ui.viewmodel.AnasayfaViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import androidx.compose.runtime.collectAsState
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(navController: NavController, anasayfaViewModel: AnasayfaViewModel) {

    //Film listesi ve favori filmler için state tanımlamaları
    val filmlerListesi = anasayfaViewModel.filmlerListesi.observeAsState(listOf())
    val favoriteFilms by anasayfaViewModel.favoriteFilms.collectAsState()
    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val filmAra = remember { mutableStateOf("") }

    //Uygulama başladığında filmleri yükleme
    LaunchedEffect(key1 = true) {
        anasayfaViewModel.filmleriYukle()
    }

    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(
                        WindowInsetsSides.Top)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff190e36),
                    titleContentColor = Color(0xffb8b7bf),
                    actionIconContentColor = Color(0xffb8b7bf),
                    navigationIconContentColor = Color(0xffb8b7bf)
                ),
                title = {
                    if (aramaYapiliyorMu.value) {
                        TextField(
                            value = filmAra.value,
                            onValueChange = {
                                filmAra.value = it
                                anasayfaViewModel.ara(it)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                focusedIndicatorColor = Color(0xffb8b7bf),
                                unfocusedLabelColor = Color.Black,
                                unfocusedIndicatorColor = Color(0xffb8b7bf),
                                cursorColor = Color(0xffb8b7bf),
                                focusedTextColor = Color(0xffb8b7bf),
                                unfocusedTextColor = Color(0xffb8b7bf),
                                disabledTextColor = Color(0xffb8b7bf),
                            ),
                            maxLines = 1
                        )
                    } else {
                    Text(
                        "MOVIEUM",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    ) }
                },
                actions = {
                    if (aramaYapiliyorMu.value) {
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = false
                            filmAra.value = ""
                            navController.navigate("anasayfa")
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "",
                            )
                        }
                    } else {
                        IconButton(onClick = { aramaYapiliyorMu.value = true }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = ""
                            )
                        }
                    }
                }
            )
        },

        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    containerColor = Color.Black.copy(alpha = 0.4f)
                ) {

                }
                BottomBar(navController = navController)
            }
        }


        ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(color = Color(0xff190e36))
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {



            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp)
            ) {
                items(
                    count = filmlerListesi.value.count(),
                    itemContent = {
                        val film = filmlerListesi.value[it]
                        Card(modifier = Modifier.padding(all = 5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xff2d204b),
                                contentColor = Color(0xffcccbd3))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val jsonFilm = Gson().toJson(film)
                                        navController.navigate("detay/$jsonFilm") },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(all = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = film.name, style = MaterialTheme.typography.headlineLarge)
                                    Text(text = film.director, style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Box(modifier = Modifier
                                        .size(200.dp, 300.dp)
                                        .clip(RoundedCornerShape(5.dp))) {
                                    val url = "http://kasimadalan.pe.hu/movies/images/${film.image}"
                                    GlideImage(
                                        imageModel = url,
                                        modifier = Modifier
                                              .fillMaxSize()
                                    )
                                        Icon(
                                            Icons.Rounded.Star,
                                            contentDescription = "Favori",
                                            tint = if (favoriteFilms.contains(film.id)) Color.Yellow else Color.White,
                                            modifier = Modifier
                                                .size(26.dp)
                                                .align(Alignment.TopStart)
                                                .background(Color(0xffa0a0c2).copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .clickable { anasayfaViewModel.toggleFavorite(film.id) }
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.year}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.category}",
                                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.rating}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(text = "₺${film.price}", style = MaterialTheme.typography.headlineLarge)
                                    Spacer(modifier = Modifier.size(3.dp))
                                    Button(
                                        onClick = {
                                            val jsonFilm = Gson().toJson(film)
                                            navController.navigate("detay/$jsonFilm") },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xffa0a0c2),
                                            contentColor = Color(0xff332d53)),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = "Göz At")
                                    }
                                }
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.size(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Aksiyon",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffb8b7bf)
                )
            }

            //"Aksiyon" kategorisi için LazyRow ekleme
            val actionFilmler = filmlerListesi.value.filter { it.category == "Action" }


                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp)
                ) {
                    items(
                        count = actionFilmler.size,
                        itemContent = {
                            val film = actionFilmler[it]
                            Card(modifier = Modifier.padding(all = 5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xff2d204b),
                                    contentColor = Color(0xffcccbd3))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val jsonFilm = Gson().toJson(film)
                                            navController.navigate("detay/$jsonFilm") },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(all = 10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = film.name, style = MaterialTheme.typography.headlineLarge)
                                        Text(text = film.director, style = MaterialTheme.typography.bodyLarge)
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(200.dp, 300.dp)
                                                .clip(RoundedCornerShape(5.dp))
                                        ) {
                                            val url = "http://kasimadalan.pe.hu/movies/images/${film.image}"
                                            GlideImage(
                                                imageModel = url,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                            Icon(
                                                Icons.Rounded.Star,
                                                contentDescription = "Favori",
                                                tint = if (favoriteFilms.contains(film.id)) Color.Yellow else Color.White,
                                                modifier = Modifier
                                                    .size(26.dp)
                                                    .align(Alignment.TopStart)
                                                    .background(Color(0xffa0a0c2).copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                    .clickable { anasayfaViewModel.toggleFavorite(film.id) }
                                                    .padding(4.dp)
                                            )
                                            Text(
                                                text = "${film.year}",
                                                style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                                modifier = Modifier
                                                    .align(Alignment.BottomEnd)
                                                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                    .padding(4.dp)
                                            )
                                            Text(
                                                text = "${film.category}",
                                                style = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                                                modifier = Modifier
                                                    .align(Alignment.BottomStart)
                                                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                    .padding(4.dp)
                                            )
                                            Text(
                                                text = "${film.rating}",
                                                style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                    .padding(4.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(text = "₺${film.price}", style = MaterialTheme.typography.headlineLarge)
                                        Spacer(modifier = Modifier.size(3.dp))
                                        Button(
                                            onClick = {
                                                val jsonFilm = Gson().toJson(film)
                                                navController.navigate("detay/$jsonFilm") },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xffa0a0c2),
                                                contentColor = Color(0xff332d53)),
                                            shape = RoundedCornerShape(8.dp),
                                        ) {
                                            Text(text = "Göz At")
                                        }
                                    }
                                }
                            }
                        }
                    )
                }

            Spacer(modifier = Modifier.size(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Bilim Kurgu",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffb8b7bf)
                )
            }

            //"Bilim Kurgu" kategorisi için LazyRow ekleme
            val scienceFictionFilmler = filmlerListesi.value.filter { it.category == "Science Fiction" }


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp)
            ) {
                items(
                    count = scienceFictionFilmler.size,
                    itemContent = {
                        val film = scienceFictionFilmler[it]
                        Card(modifier = Modifier.padding(all = 5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xff2d204b),
                                contentColor = Color(0xffcccbd3))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val jsonFilm = Gson().toJson(film)
                                        navController.navigate("detay/$jsonFilm") },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(all = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = film.name, style = MaterialTheme.typography.headlineLarge)
                                    Text(text = film.director, style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(200.dp, 300.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                    ) {
                                        val url = "http://kasimadalan.pe.hu/movies/images/${film.image}"
                                        GlideImage(
                                            imageModel = url,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Icon(
                                            Icons.Rounded.Star,
                                            contentDescription = "Favori",
                                            tint = if (favoriteFilms.contains(film.id)) Color.Yellow else Color.White,
                                            modifier = Modifier
                                                .size(26.dp)
                                                .align(Alignment.TopStart)
                                                .background(Color(0xffa0a0c2).copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .clickable { anasayfaViewModel.toggleFavorite(film.id) }
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.year}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.category}",
                                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.rating}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(text = "₺${film.price}", style = MaterialTheme.typography.headlineLarge)
                                    Spacer(modifier = Modifier.size(3.dp))
                                    Button(
                                        onClick = {
                                            val jsonFilm = Gson().toJson(film)
                                            navController.navigate("detay/$jsonFilm") },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xffa0a0c2),
                                            contentColor = Color(0xff332d53)),
                                        shape = RoundedCornerShape(8.dp),
                                    ) {
                                        Text(text = "Göz At")
                                    }
                                }
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.size(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Dram",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffb8b7bf)
                )
            }

            //"Dram" kategorisi için LazyRow ekleme
            val dramaFilmler = filmlerListesi.value.filter { it.category == "Drama" }


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp)
            ) {
                items(
                    count = dramaFilmler.size,
                    itemContent = {
                        val film = dramaFilmler[it]
                        Card(modifier = Modifier.padding(all = 5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xff2d204b),
                                contentColor = Color(0xffcccbd3))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val jsonFilm = Gson().toJson(film)
                                        navController.navigate("detay/$jsonFilm") },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(all = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = film.name, style = MaterialTheme.typography.headlineLarge)
                                    Text(text = film.director, style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(200.dp, 300.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                    ) {
                                        val url = "http://kasimadalan.pe.hu/movies/images/${film.image}"
                                        GlideImage(
                                            imageModel = url,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Icon(
                                            Icons.Rounded.Star,
                                            contentDescription = "Favori",
                                            tint = if (favoriteFilms.contains(film.id)) Color.Yellow else Color.White,
                                            modifier = Modifier
                                                .size(26.dp)
                                                .align(Alignment.TopStart)
                                                .background(Color(0xffa0a0c2).copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .clickable { anasayfaViewModel.toggleFavorite(film.id) }
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.year}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.category}",
                                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.rating}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(text = "₺${film.price}", style = MaterialTheme.typography.headlineLarge)
                                    Spacer(modifier = Modifier.size(3.dp))
                                    Button(
                                        onClick = {
                                            val jsonFilm = Gson().toJson(film)
                                            navController.navigate("detay/$jsonFilm") },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xffa0a0c2),
                                            contentColor = Color(0xff332d53)),
                                        shape = RoundedCornerShape(8.dp),
                                    ) {
                                        Text(text = "Göz At")
                                    }
                                }
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.size(20.dp))


            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Fantastik",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffb8b7bf)
                )
            }

            //"Fantastik" kategorisi için LazyRow ekleme
            val fantasticFilmler = filmlerListesi.value.filter { it.category == "Fantastic" }


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 5.dp)
            ) {
                items(
                    count = fantasticFilmler.size,
                    itemContent = {
                        val film = fantasticFilmler[it]
                        Card(modifier = Modifier.padding(all = 5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xff2d204b),
                                contentColor = Color(0xffcccbd3))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val jsonFilm = Gson().toJson(film)
                                        navController.navigate("detay/$jsonFilm") },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(all = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = film.name, style = MaterialTheme.typography.headlineLarge)
                                    Text(text = film.director, style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(200.dp, 300.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                    ) {
                                        val url = "http://kasimadalan.pe.hu/movies/images/${film.image}"
                                        GlideImage(
                                            imageModel = url,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Icon(
                                            Icons.Rounded.Star,
                                            contentDescription = "Favori",
                                            tint = if (favoriteFilms.contains(film.id)) Color.Yellow else Color.White,
                                            modifier = Modifier
                                                .size(26.dp)
                                                .align(Alignment.TopStart)
                                                .background(Color(0xffa0a0c2).copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .clickable { anasayfaViewModel.toggleFavorite(film.id) }
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.year}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.category}",
                                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                        Text(
                                            text = "${film.rating}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(3.dp))
                                                .padding(4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(text = "₺${film.price}", style = MaterialTheme.typography.headlineLarge)
                                    Spacer(modifier = Modifier.size(3.dp))
                                    Button(
                                        onClick = {
                                            val jsonFilm = Gson().toJson(film)
                                            navController.navigate("detay/$jsonFilm") },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xffa0a0c2),
                                            contentColor = Color(0xff332d53)),
                                        shape = RoundedCornerShape(8.dp),
                                    ) {
                                        Text(text = "Göz At")
                                    }
                                }
                            }
                        }
                    }
                )
            }

        }

    }


}

//BottomBar tanımlama
@Composable
fun BottomBar(navController: NavController) {
    //Mevcut kullanıcı bilgisini alma
    val currentUser = FirebaseAuth.getInstance().currentUser
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarNesne(
            icon = Icons.Rounded.Home,
            label = "Ana Sayfa",
            onClick = {
                navController.navigate("anasayfa") {
                    popUpTo("anasayfa") { inclusive = true }
                }
            }
        )
        BottomBarNesne(
            icon = painterResource(id = R.drawable.promo2),
            label = "Kupon",
            onClick = { navController.navigate("kupon")},
        )
        BottomBarNesne(
            icon = Icons.Rounded.ShoppingCart,
            label = "Sepet",
            onClick = {
                if (currentUser != null) {
                    navController.navigate("sepet/${currentUser.email}") {
                        popUpTo("detay") { inclusive = true }
                    }
                } else {
                    navController.navigate("login")
                }
            }
        )
        BottomBarNesne(
            icon = Icons.Rounded.Person,
            label = "Profil",
            onClick = {
                navController.navigate("profile")
            }
        )
    }
}

//BottomBarNesne tanımlama
@Composable
fun BottomBarNesne(icon: Any, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon is ImageVector) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        } else if (icon is Painter) {
            Icon(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}


