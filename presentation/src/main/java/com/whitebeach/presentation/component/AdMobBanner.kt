package com.whitebeach.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdSize
//import com.google.android.gms.ads.AdView
import com.whitebeach.presentation.R

@Composable
fun AdaptiveBanner(
    modifier: Modifier = Modifier,
) {
    val deviceCurrentWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
//        AndroidView(
//            modifier = Modifier.fillMaxWidth(),
//            factory = {context ->
//                // Using application context to avoid memory leak
//                AdView(context).apply {
//                    setAdSize(
//                        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
//                            context,
//                            deviceCurrentWidth,
//                        ),
//                    )
//                    adUnitId = context.getString(R.string.ad_id_banner)
//                    loadAd(AdRequest.Builder().build())
//                }
//            },
//        )
    }
}