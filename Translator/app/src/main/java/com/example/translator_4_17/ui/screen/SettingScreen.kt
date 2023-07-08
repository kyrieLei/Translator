package com.example.translator_4_17.ui.screen


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.translator_4_17.R
import com.example.translator_4_17.myDefinitionPart.SliderPreference
import com.example.translator_4_17.myDefinitionPart.StyledIconButton
import com.example.translator_4_17.myDefinitionPart.SwitchPreference
import com.example.translator_4_17.util.Preferences

@Suppress("KotlinConstantConditions")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {

    val imageModifier = Modifier
        .size(500.dp)
        .alpha(0.5f)

    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.options)
                    )
                },
                navigationIcon = {
                    StyledIconButton(
                        imageVector = Icons.Default.ArrowBack
                    ) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) { pV ->
        Column(
            modifier = Modifier
                .padding(pV)
                .fillMaxSize()
                .padding(15.dp, 0.dp)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {

                    var translateAutomatically by remember {
                        mutableStateOf(Preferences.get(Preferences.translateAutomatically, true))
                    }

                    SwitchPreference(
                        preferenceKey = Preferences.translateAutomatically,
                        defaultValue = true,
                        preferenceTitle = stringResource(R.string.translate_automatically),
                    ) {
                        translateAutomatically = it
                    }

                    AnimatedVisibility(visible = translateAutomatically) {
                        SliderPreference(
                            preferenceKey = Preferences.fetchDelay,
                            preferenceTitle = stringResource(R.string.fetch_delay),
                            defaultValue = 30f,
                            minValue = 10f,
                            maxValue = 100f,
                            stepSize = 10f
                        )
                    }
                    SwitchPreference(
                        preferenceKey = Preferences.showAdditionalInfo,
                        defaultValue = true,
                        preferenceTitle = stringResource(R.string.additional_info),
                    )

                }
                item {

                    SwitchPreference(
                        preferenceKey = Preferences.historyEnabledKey,
                        defaultValue = true,
                        preferenceTitle = stringResource(R.string.history_enabled),
                    )
                }

                item {

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.slam_dunk), // 从资源文件中加载图片
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds, // 设置图片的缩放模式为适应
                            modifier = imageModifier // 应用修饰器
                        )
                    }
                }


            }
        }
    }

}
