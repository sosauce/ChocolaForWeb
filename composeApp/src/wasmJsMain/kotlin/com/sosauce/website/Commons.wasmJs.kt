package com.sosauce.website

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import io.github.vinceglb.filekit.PlatformFile
import org.w3c.dom.url.URL
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape

actual fun PlatformFile.getUri() = URL.createObjectURL(this.file)

actual fun ArtShape(): Shape {
    return SquircleShape(
        percent = 35,
        smoothing = CornerSmoothing.Full
    )
}