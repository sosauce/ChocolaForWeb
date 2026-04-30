@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)

package com.sosauce.website

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.nameWithoutExtension
import io.github.vinceglb.filekit.readString
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.fast_forward
import website.composeapp.generated.resources.fast_rewind
import website.composeapp.generated.resources.github
import website.composeapp.generated.resources.image
import website.composeapp.generated.resources.nunito_extrabold
import website.composeapp.generated.resources.pause
import website.composeapp.generated.resources.play
import website.composeapp.generated.resources.repeat_one
import website.composeapp.generated.resources.skip_next
import website.composeapp.generated.resources.skip_previous
import kotlin.js.toJsReference
import kotlin.math.round
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun App() {


    val player = retain { getPlayer() }
    var name by retain { mutableStateOf<String?>(null) }
    val uriHandler = LocalUriHandler.current
    val state by player.state.collectAsStateWithLifecycle()
    val launcher = rememberFilePickerLauncher { file ->
        file?.let {
            player.loadFile(it)
            name = it.nameWithoutExtension
        }
    }

    ChocolaTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .safeContentPadding()
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Chocola for Web")
                        IconButton(
                            onClick = { uriHandler.openUri("https://github.com/sosauce") },
                            shapes = IconButtonDefaults.shapes()
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.github),
                                contentDescription = null
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Text("made with <3 by sosauce")
                    }
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = state.isLoaded,
                    enter = slideInVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) { it } + fadeIn(),
                    exit = slideOutVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) { it } + fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .safeContentPadding()
                            .padding(10.dp)
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var tempValue by remember { mutableStateOf<Float?>(null) }

                            Slider(
                                modifier = Modifier.width(200.dp),
                                value = tempValue ?: state.rate.toFloat(),
                                onValueChange = { tempValue = it },
                                onValueChangeFinished = {
                                    tempValue?.let {
                                        player.changeRate(it)
                                    }
                                    tempValue = null
                                },
                                valueRange = 0.5f..2f,
                                track = { sliderState ->
                                    SliderDefaults.Track(
                                        sliderState = sliderState,
                                        drawStopIndicator = null,
                                        thumbTrackGapSize = 0.dp,
                                        modifier = Modifier.height(4.dp)
                                    )
                                },
                                thumb = { sliderState ->
                                    val width by animateDpAsState(
                                        targetValue = if (sliderState.isDragging) 28.dp else 20.dp
                                    )
                                    SliderDefaults.Thumb(
                                        interactionSource = remember { MutableInteractionSource() },
                                        thumbSize = DpSize(
                                            width = width,
                                            height = 20.dp
                                        )
                                    )
                                }
                            )
                            Text(
                                text = "x${(tempValue?.toDouble() ?: state.rate).round(2)}"
                            )
                            Spacer(Modifier.weight(1f))
                            Button(
                                onClick = launcher::launch,
                                shapes = ButtonDefaults.shapes()
                            ) {
                                Text("Load new")
                            }
                            Spacer(Modifier.weight(1f))

                            val repeatColor by animateColorAsState(
                                if (state.isLooping) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh
                            )

                            IconButton(
                                onClick = player::toggleLoop,
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = repeatColor,
                                    contentColor = contentColorFor(repeatColor)
                                ),
                                shapes = IconButtonDefaults.shapes(),
                                modifier = Modifier.size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide))
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.repeat_one),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->


            if (!state.isLoaded) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = launcher::launch,
                        shapes = ButtonDefaults.shapes()
                    ) {
                        Text("Load audio file")
                    }
                }
            } else {
                if (state.isBuffering) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ContainedLoadingIndicator()
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .clip(ArtShape())
                        )
                        Spacer(Modifier.width(15.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = name ?: "<unknown>",
                                style = MaterialTheme.typography.displayLargeEmphasized
                            )
                            Text(
                                text = "<unknown>",
                                style = MaterialTheme.typography.displayMediumEmphasized.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            var tempSliderValue by remember { mutableStateOf<Float?>(null)}
                            val animatedValue by animateFloatAsState(tempSliderValue ?: state.positionInMillis.toFloat())
                            Spacer(Modifier.height(20.dp))


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = state.positionInMillis.formatToReadableTime(),
                                    style = MaterialTheme.typography.headlineSmallEmphasized.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(
                                    text = state.durationMillis.formatToReadableTime(),
                                    style = MaterialTheme.typography.headlineSmallEmphasized.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                            Slider(
                                value = animatedValue,
                                onValueChange = { tempSliderValue = it },
                                onValueChangeFinished = {
                                    tempSliderValue?.let { player.seekTo(it.toInt()) }
                                    tempSliderValue = null
                                },
                                valueRange = 0f..(state.durationMillis.toFloat()),
                                track = { sliderState ->
                                    val animatedHeight by animateDpAsState(
                                        if (sliderState.isDragging) 7.dp else 4.dp
                                    )
                                    val trackStroke = Stroke(
                                        width =
                                            with(LocalDensity.current) {
                                                animatedHeight.toPx()
                                            },
                                        cap = StrokeCap.Round,
                                    )

                                    LinearWavyProgressIndicator(
                                        modifier = Modifier.fillMaxWidth(),
                                        progress = {
                                            val rangeLength = sliderState.valueRange.endInclusive - sliderState.valueRange.start
                                            if (rangeLength > 0f) {
                                                (sliderState.value - sliderState.valueRange.start) / rangeLength
                                            } else 0f
                                        },
                                        stopSize = 0.dp,
                                        trackStroke = trackStroke,
                                        amplitude = { if (!sliderState.isDragging && state.isPlaying) 1f else 0f }
                                    )
                                },
                                thumb = { sliderState ->
                                    val animatedHeight by animateDpAsState(
                                        if (sliderState.isDragging) 40.dp else 35.dp
                                    )

                                    val animatedWidth by animateDpAsState(
                                        if (sliderState.isDragging) 10.dp else 6.dp
                                    )

                                    SliderDefaults.Thumb(
                                        interactionSource = remember { MutableInteractionSource() },
                                        thumbSize = DpSize(animatedWidth, animatedHeight)
                                    )
                                }
                            )


                            val interactionSources = List(3) { remember { MutableInteractionSource() } }
                            Spacer(Modifier.height(20.dp))

                            ButtonGroup(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                overflowIndicator = {}
                            ) {
                                customItem(
                                    {
                                        IconButton(
                                            onClick = player::fastRewind,
                                            shapes = IconButtonDefaults.shapes(),
                                            colors = IconButtonDefaults.filledIconButtonColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                                contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainer)
                                            ),
                                            interactionSource = interactionSources[0],
                                            modifier = Modifier
                                                .weight(1f)
                                                .size(IconButtonDefaults.mediumContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide))
                                                .animateWidth(interactionSource = interactionSources[0])
                                        ) {
                                            Icon(
                                                painter = painterResource(Res.drawable.fast_rewind),
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    {}
                                )
                                customItem(
                                    {
                                        FilledIconButton(
                                            onClick = player::playOrPause,
                                            shapes = IconButtonDefaults.shapes(),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = MaterialTheme.colorScheme.primary,
                                                contentColor = contentColorFor(MaterialTheme.colorScheme.primary)
                                            ),
                                            interactionSource = interactionSources[1],
                                            modifier = Modifier
                                                .weight(1.5f)
                                                .size(IconButtonDefaults.mediumContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide))
                                                .animateWidth(interactionSource = interactionSources[1])
                                        ) {
                                            val icon = if (state.isPlaying) Res.drawable.pause else Res.drawable.play
                                            Icon(
                                                painter = painterResource(icon),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
//                                            AnimatedPlayPauseIcon(
//                                                isPlaying = musicState.isPlaying
//                                            )
                                        }
                                    },
                                    {}
                                )
                                customItem(
                                    {
                                        IconButton(
                                            onClick = player::fastForward,
                                            shapes = IconButtonDefaults.shapes(),
                                            colors = IconButtonDefaults.filledIconButtonColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                                contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainer)
                                            ),
                                            interactionSource = interactionSources[2],
                                            modifier = Modifier
                                                .weight(1f)
                                                .size(IconButtonDefaults.mediumContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide))
                                                .animateWidth(interactionSource = interactionSources[2])
                                        ) {
                                            Icon(
                                                painter = painterResource(Res.drawable.fast_forward),
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    {}
                                )

                            }
                        }

                    }


                    }
                }
            }


        }

    }

fun Int.formatToReadableTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    // %02d means: "format as an integer, at least 2 digits, pad with 0"
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

