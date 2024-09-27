package com.codek.monitorumidade.presentation.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Green100 = Color(0xFFC9FCAD)
val Green200 = Color(0xFFAEFF82)
val Green400 = Color(0xFF8BEB4C)
val Green500 = Color(0xFF07A312)
val Green700 = Color(0xFF007033)

val DarkColor = Color(0xFF101522)
val DarkColor2 = Color(0xFF202532)

val GreenGradient = Brush.linearGradient(
    colors = listOf(Green100, Green200),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val GreenGrade4 = Color(0xFF00FF00) // Verde puro
val GreenGrade3 = Color(0xFF38FF38) // Verde claro
val GreenGrade2 = Color(0xFF6BFF6B) // Verde suave
val GreenGrade = Color(0xFFC0FF38) // Verde limão
val YellowGrade = Color(0xFFFFF538) // Amarelo
val YellowGrade2 = Color(0xFFFFE700) // Amarelo mais suave
val OrangeGrade = Color(0xFFFF9538) // Laranja
val RedGrade = Color(0xFFFF3838) // Vermelho

val DarkGradient = Brush.verticalGradient(
    colors = listOf(DarkColor2, DarkColor)
)

//Color(0xFF24C72F)
//Color(0xFF22F030)