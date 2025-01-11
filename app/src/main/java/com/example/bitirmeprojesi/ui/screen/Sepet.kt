package com.example.bitirmeprojesi.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bitirmeprojesi.data.entity.Sepet
import com.example.bitirmeprojesi.ui.viewmodel.SepetViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sepet(
    sepetViewModel: SepetViewModel,
    userName: String,
    navController: NavController
) {
    //Snackbar ve sepet listesi için state tanımlamaları
    val snackbarHostState = remember { SnackbarHostState() }
    val sepetListesi = sepetViewModel.sepetListesi.observeAsState(emptyList()).value
    val coroutineScope = rememberCoroutineScope()

    //Kupon kodu girişi ve indirim durumu için state tanımlamaları
    val kuponKodu = remember { mutableStateOf("") }
    val indirimOrani = remember { mutableStateOf(0f) }

    //Kupon kodlarını ve indirim oranlarını tanımlama
    val gecerliKuponlar = mapOf(
        "ZK8B7L" to 0.25f,
        "PTL74Q" to 0.50f,
        "5RSW1N" to 0.75f
    )

    //Kullanıcının sepetini yükleme
    LaunchedEffect(userName) {
        sepetViewModel.sepetiGetir(userName)
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
                title = { Text("Sepet") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("anasayfa") }) {
                        Icon(Icons.Default.Close, contentDescription = "Geri")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(if (indirimOrani.value > 0) 200.dp else 180.dp),
                containerColor = Color.Black.copy(alpha = 0.4f)
            ) {
                val toplamUcret = sepetListesi.sumOf { it.price * it.orderAmount }
                val indirimliToplam = (toplamUcret * (1 - indirimOrani.value)).toInt()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = kuponKodu.value,
                            onValueChange = { kuponKodu.value = it.uppercase() },
                            placeholder = {
                                Text(
                                    "Kupon Kodunu Gir",
                                    color = Color(0xffb8b7bf).copy(alpha = 0.6f),
                                    fontSize = 14.sp,
                                    style = TextStyle(
                                        lineHeight = 14.sp,
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    )
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xff2d204b),
                                unfocusedBorderColor = Color(0xff2d204b),
                                focusedTextColor = Color(0xffb8b7bf),
                                unfocusedTextColor = Color(0xffb8b7bf),
                                cursorColor = Color(0xffb8b7bf),
                            ),
                            maxLines = 1,
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    val yeniIndirimOrani = gecerliKuponlar[kuponKodu.value]
                                    if (yeniIndirimOrani != null) {
                                        indirimOrani.value = yeniIndirimOrani
                                        snackbarHostState.showSnackbar("Kupon kodu başarıyla uygulandı!")
                                    } else {
                                        snackbarHostState.showSnackbar("Geçersiz kupon kodu!")
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff2d204b),
                                contentColor = Color(0xffcccbd3)
                            ),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Uygula",
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        if (indirimOrani.value > 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "İndirimsiz Toplam: ₺$toplamUcret",
                                    color = Color(0xffb8b7bf).copy(alpha = 0.7f),
                                    fontSize = 13.sp,
                                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                )
                                Text(
                                    text = "İndirim: %${(indirimOrani.value * 100).toInt()}",
                                    color = Color(0xffb8b7bf).copy(alpha = 0.7f),
                                    fontSize = 13.sp
                                )
                            }
                            Text(
                                text = "İndirimli Toplam: ₺${indirimliToplam}",
                                color = Color(0xffb8b7bf),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        } else {
                            Text(
                                text = "Toplam: ₺$toplamUcret",
                                color = Color(0xffb8b7bf),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                sepetViewModel.sepetiTemizle(userName)
                                navController.navigate("anasayfa")
                                snackbarHostState.showSnackbar("Sepetin onaylandı!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff2d204b),
                            contentColor = Color(0xffcccbd3)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Sepeti Onayla",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = Color(0xff190e36))
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (sepetListesi.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sepetin Boş!",
                        color = Color(0xffb8b7bf),
                        fontSize = 22.sp
                    )
                }
            } else {
                LazyColumn {
                    items(sepetListesi) { film ->
                        SepetItem(
                            film = film,
                            sepetViewModel = sepetViewModel,
                            userName = userName
                        )
                    }
                }
            }
        }
    }
}

//Sepetteki her bir ürünü gösteren composable fonksiyon
@Composable
fun SepetItem(film: Sepet, sepetViewModel: SepetViewModel, userName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff2d204b),
            contentColor = Color(0xffcccbd3)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    imageModel = "http://kasimadalan.pe.hu/movies/images/${film.image}",
                    modifier = Modifier
                        .size(100.dp, 150.dp)
                        .clip(RoundedCornerShape(5.dp))
                )
                Column {
                    Text(text = film.name, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Fiyat: ₺${film.price}")
                    Text(text = "Adet: ${film.orderAmount}")
                    Text(text = "Toplam: ₺${film.price * film.orderAmount}")
                }
                IconButton(
                    onClick = {
                        sepetViewModel.sil(film.cartId, userName)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Sil",
                    )
                }
            }
        }
    }}