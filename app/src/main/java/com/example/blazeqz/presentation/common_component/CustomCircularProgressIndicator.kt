package com.example.blazeqz.presentation.common_component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blazeqz.presentation.theme.gray
import com.example.blazeqz.presentation.theme.orange
import com.example.blazeqz.presentation.theme.white
//import com.kapps.circularprogressindicatoryt.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    initialValue: Float,
    primaryColor: Color,
    secondaryColor: Color,
    minValue: Int = 0,
    maxValue: Int = 100,
    circleRadius: Float,
    onPositionChange: (Int) -> Unit
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            // Ensure the circle fits within the canvas by calculating dynamic radius
            val dynamicRadius = minOf(width, height) * 0.4f
            circleCenter = Offset(x = width / 2f, y = height / 2f)

            // Scale thickness to match canvas size
            val circleThickness = width / 20f

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )
                ),
                radius = dynamicRadius,
                center = circleCenter
            )

            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = dynamicRadius,
                center = circleCenter
            )

            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f / maxValue) * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = dynamicRadius * 2f,
                    height = dynamicRadius * 2f
                ),
                topLeft = Offset(
                    (width - dynamicRadius * 2f) / 2f,
                    (height - dynamicRadius * 2f) / 2f
                )
            )

            val outerRadius = dynamicRadius + circleThickness / 2f
            val gap = width / 25f  // Scale gap based on width

            for (i in 0..(maxValue - minValue)) {
                val color = if (i < positionValue - minValue) primaryColor else primaryColor.copy(alpha = 0.3f)
                val angleInDegrees = i * 360f / (maxValue - minValue).toFloat()
                val angleInRad = angleInDegrees * PI / 180f + PI / 2f

                val yGapAdjustment = cos(angleInDegrees * PI / 180f) * gap
                val xGapAdjustment = -sin(angleInDegrees * PI / 180f) * gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val tickLength = circleThickness * 0.8f  // Scale tick length based on thickness
                val end = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + tickLength + circleCenter.y + yGapAdjustment).toFloat()
                )

                rotate(
                    angleInDegrees,
                    pivot = start
                ) {
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }

            // Scale text size relative to canvas size
            val textSize = minOf(width, height) * 0.15f

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$positionValue %",
                        circleCenter.x,
                        circleCenter.y + textSize / 3f,
                        Paint().apply {
                            this.textSize = textSize
                            textAlign = Paint.Align.CENTER
                            color = white.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CustomCircularProgressIndicator(
        modifier = Modifier
            .size(250.dp)
            .background(Color.DarkGray)
        ,
        initialValue = 30.5f,
        primaryColor = orange,
        secondaryColor = gray,
        circleRadius = 230f,
        onPositionChange = {

        }
    )
}