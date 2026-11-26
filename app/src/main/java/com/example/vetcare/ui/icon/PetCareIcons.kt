package com.example.vetcare.ui.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object PetCareIcons {
    private val NavyBlue = Color(0xFF213A63)
    private val TealAccent = Color(0xFF24A8C2)

    val FoodBowl: ImageVector by lazy {
        ImageVector.Builder(
            name = "FoodBowl",
            defaultWidth = 36.dp,
            defaultHeight = 36.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Kibble pile
            path(fill = SolidColor(TealAccent)) {
                moveTo(6f, 12f)
                curveTo(6f, 8.5f, 9f, 7f, 12f, 7f)
                curveTo(15f, 7f, 18f, 8.5f, 18f, 12f)
                close()
            }
            // Bowl base
            path(fill = SolidColor(NavyBlue)) {
                moveTo(4f, 12f)
                lineTo(20f, 12f)
                lineTo(22f, 19f)
                lineTo(2f, 19f)
                close()
            }
            // Bone emblem
            path(fill = SolidColor(Color.Companion.White)) {
                moveTo(9.5f, 14.8f)
                horizontalLineTo(14.5f)
                verticalLineTo(16.2f)
                horizontalLineTo(9.5f)
                close()
            }
        }.build()
    }

    val PharmacyCross: ImageVector by lazy {
        ImageVector.Builder(
            name = "PharmacyCross",
            defaultWidth = 36.dp,
            defaultHeight = 36.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Syringe diagonals
            path(
                stroke = SolidColor(TealAccent),
                strokeLineWidth = 1.8f,
                strokeLineCap = StrokeCap.Companion.Round
            ) {
                moveTo(5f, 5f)
                lineTo(19f, 19f)
                moveTo(19f, 5f)
                lineTo(5f, 19f)
            }
            // Medical cross center
            path(fill = SolidColor(NavyBlue)) {
                moveTo(10.5f, 7f)
                horizontalLineTo(13.5f)
                verticalLineTo(17f)
                horizontalLineTo(10.5f)
                close()
                moveTo(7f, 10.5f)
                horizontalLineTo(17f)
                verticalLineTo(13.5f)
                horizontalLineTo(7f)
                close()
            }
        }.build()
    }

    val PetBoarding: ImageVector by lazy {
        ImageVector.Builder(
            name = "PetBoarding",
            defaultWidth = 36.dp,
            defaultHeight = 36.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Roof
            path(fill = SolidColor(NavyBlue)) {
                moveTo(12f, 3f)
                lineTo(2f, 11f)
                lineTo(4f, 13f)
                lineTo(12f, 6.5f)
                lineTo(20f, 13f)
                lineTo(22f, 11f)
                close()
            }
            // House body
            path(fill = SolidColor(TealAccent)) {
                moveTo(5f, 11f)
                verticalLineTo(20f)
                horizontalLineTo(19f)
                verticalLineTo(11f)
                lineTo(12f, 5.5f)
                close()
            }
            // Doorway
            path(fill = SolidColor(NavyBlue)) {
                moveTo(9.5f, 20f)
                verticalLineTo(15f)
                curveTo(9.5f, 13.6f, 10.6f, 12.5f, 12f, 12.5f)
                curveTo(13.4f, 12.5f, 14.5f, 13.6f, 14.5f, 15f)
                verticalLineTo(20f)
                close()
            }
        }.build()
    }

    val VeterinaryBadge: ImageVector by lazy {
        ImageVector.Builder(
            name = "VeterinaryBadge",
            defaultWidth = 36.dp,
            defaultHeight = 36.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Doctor silhouette
            path(fill = SolidColor(NavyBlue)) {
                moveTo(12f, 4f)
                curveTo(14.2f, 4f, 16f, 5.8f, 16f, 8f)
                curveTo(16f, 10.2f, 14.2f, 12f, 12f, 12f)
                curveTo(9.8f, 12f, 8f, 10.2f, 8f, 8f)
                curveTo(8f, 5.8f, 9.8f, 4f, 12f, 4f)
                close()
                moveTo(5f, 20f)
                curveTo(5f, 16.5f, 8.1f, 14f, 12f, 14f)
                curveTo(15.9f, 14f, 19f, 16.5f, 19f, 20f)
                close()
            }
            // Teal medical heart badge
            path(fill = SolidColor(TealAccent)) {
                moveTo(16.5f, 14.5f)
                curveTo(18.4f, 14.5f, 20f, 16.1f, 20f, 18f)
                curveTo(20f, 19.9f, 18.4f, 21.5f, 16.5f, 21.5f)
                curveTo(14.6f, 21.5f, 13f, 19.9f, 13f, 18f)
                curveTo(13f, 16.1f, 14.6f, 14.5f, 16.5f, 14.5f)
                close()
            }
        }.build()
    }
}