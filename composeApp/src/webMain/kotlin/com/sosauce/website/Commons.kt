package com.sosauce.website

import androidx.compose.ui.graphics.Shape
import io.github.vinceglb.filekit.PlatformFile

expect fun PlatformFile.getUri(): String

expect fun ArtShape(): Shape

