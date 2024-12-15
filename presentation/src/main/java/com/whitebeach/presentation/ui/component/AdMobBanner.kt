package com.whitebeach.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

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
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {context ->
                // Using application context to avoid memory leak
                com.google.android.gms.ads.AdView(context).apply {
                    com.google.android.gms.ads.BaseAdView.setAdSize(
                        com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                            context,
                            deviceCurrentWidth,
                        ),
                    )
                    com.google.android.gms.ads.BaseAdView.setAdUnitId = context.getString(com.whitebeach.atleticolineupapp.R.string.ad_id_banner)
                    com.google.android.gms.ads.BaseAdView.loadAd(com.google.android.gms.ads.AdRequest.Builder.build())
                }
            },
        )
    }
}