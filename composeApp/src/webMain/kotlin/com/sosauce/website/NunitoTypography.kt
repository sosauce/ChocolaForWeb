@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.website

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import website.composeapp.generated.resources.Res
import website.composeapp.generated.resources.nunito_extrabold

@Composable
fun nunitoFontFamily() = FontFamily(
    Font(Res.font.nunito_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
)

@Composable
fun NunitoTypography() = Typography().run {
    copy(
        displayLarge = displayLarge.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        displayMedium = displayMedium.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        displaySmall = displaySmall.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        headlineLarge = headlineLarge.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        headlineMedium = headlineMedium.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        headlineSmall = headlineSmall.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        titleLarge = titleLarge.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        titleMedium = titleMedium.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        titleSmall = titleSmall.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        bodyLarge = bodyLarge.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        bodyMedium = bodyMedium.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        bodySmall = bodySmall.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        labelLarge = labelLarge.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        labelMedium = labelMedium.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        labelSmall = labelSmall.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        displayLargeEmphasized = displayLargeEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        displayMediumEmphasized = displayMediumEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        displaySmallEmphasized = displaySmallEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        headlineLargeEmphasized = headlineLargeEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        headlineMediumEmphasized = headlineMediumEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        headlineSmallEmphasized = headlineSmallEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        titleLargeEmphasized = titleLargeEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        titleMediumEmphasized = titleMediumEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        titleSmallEmphasized = titleSmallEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        bodyLargeEmphasized = bodyLargeEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        bodyMediumEmphasized = bodyMediumEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        bodySmallEmphasized = bodySmallEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        labelLargeEmphasized = labelLargeEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        labelMediumEmphasized = labelMediumEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        ),
        labelSmallEmphasized = labelSmallEmphasized.copy(
            fontFamily = nunitoFontFamily(),
            fontWeight = FontWeight.ExtraBold
        )
    )
}