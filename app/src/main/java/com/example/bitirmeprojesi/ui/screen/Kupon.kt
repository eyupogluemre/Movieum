package com.example.bitirmeprojesi.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.ui.viewmodel.KuponViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Kupon(
    navController: NavController,
    viewModel: KuponViewModel
) {
    //Kupon ekranı için state tanımlamaları
    val butonAktifMi by viewModel.butonAktifMi.collectAsState()
    val kuponKodu by viewModel.kuponKodu.collectAsState()
    val animasyonOynuyorMu by viewModel.animasyonOynuyorMu.collectAsState()

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
                        "Kupon",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("anasayfa") }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xff190e36))
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Çarkı Çevir, İndirimi Kap!",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffb8b7bf),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 50.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .size(300.dp)
                ) {
                    LottieAnimasyon(isPlaying = animasyonOynuyorMu)
                }

                if (kuponKodu.isNotEmpty()) {
                    Text(
                        text = "Tebrikler!",
                        color = Color(0xffb8b7bf),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "%${viewModel.indirimYuzdesiniAl()} İndirim Kupon Kodun:",
                        color = Color(0xffb8b7bf),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    KuponText(kuponKodu)
                }

                Button(
                    onClick = { viewModel.carkiCevir() },
                    enabled = butonAktifMi,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff2d204b),
                        contentColor = Color(0xffcccbd3),
                        disabledContainerColor = Color(0xff190e36),
                        disabledContentColor = Color(0xff190e36)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Çevir", fontSize = 15.sp)
                }
            }
        }
    }
}

//Lottie animasyonunu gösteren composable fonksiyon
@Composable
fun LottieAnimasyon(isPlaying: Boolean) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url("https://lottie.host/4d98d371-b330-4de2-b5f5-d43cd680d445/fpJQ295fwF.lottie")
    )
    LottieAnimation(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1
    )
}

//Kupon kodunu gösteren ve kopyalama özelliği sunan composable fonksiyon
@Composable
fun KuponText(discountCode: String) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val iconSize = 24.dp

    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff2d204b),
            contentColor = Color(0xffb8b7bf)
        ),
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = discountCode,
                color = Color(0xffb8b7bf),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.copy2),
                contentDescription = "copy",
                tint = Color(0xffb8b7bf),
                modifier = Modifier
                    .size(iconSize)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(discountCode))
                        Toast.makeText(context, "Kupon kodu kopyalandı!", Toast.LENGTH_SHORT).show()
                    }
            )
        }
    }
}






