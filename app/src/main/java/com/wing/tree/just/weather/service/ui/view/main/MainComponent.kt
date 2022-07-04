package com.wing.tree.just.weather.service.ui.view.main

import android.graphics.Paint
import android.graphics.PointF
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.wing.tree.just.weather.service.R
import com.wing.tree.just.weather.service.core.float
import com.wing.tree.just.weather.service.core.half
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

internal const val DEGREE_SIGN = "\u00B0"
internal const val VISIBLE_ITEM_COUNT = 5

@Composable
fun Forecast(
    modifier: Modifier,
    list: List<Forecast.Item>,
    visibleItemCount: Int = VISIBLE_ITEM_COUNT
) {
    if (list.isEmpty()) {
        return
    }

    val context = LocalContext.current
    val localDensity = LocalDensity.current
    val simpleDateFormat = SimpleDateFormat("a h", Locale.getDefault())

    var size by remember { mutableStateOf(IntSize.Zero) }

    val dtPaint = remember(localDensity) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = localDensity.run { 12.dp.toPx() }
            typeface = ResourcesCompat.getFont(context, R.font.jeju_font_family)
        }
    }

    val tempPaint = remember(localDensity) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = localDensity.run { 14.dp.toPx() }
            typeface = ResourcesCompat.getFont(context, R.font.jeju_font_family)
        }
    }

    val humidityPaint = remember(localDensity) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = localDensity.run { 12.dp.toPx() }
            typeface = ResourcesCompat.getFont(context, R.font.jeju_font_family)
        }
    }

    Box(modifier = modifier.onSizeChanged { size = it }) {
        Row(
            Modifier
                .height(160.dp)
                .background(Color.White)
                .horizontalScroll(rememberScrollState())
        ) {
            val spacing = size.width / visibleItemCount.float
            val waterDrop = AppCompatResources.getDrawable(context, R.drawable.ic_round_water_drop_24)?.toBitmap()
            val width = with(localDensity) { spacing.toDp() } * list.size.dec()

            Box(
                modifier = modifier
                    .padding(24.dp, 16.dp, 24.dp, 16.dp)
                    .width(width),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxWidth()) {
                    val maxTemp = list.maxOf { it.temp }

                    val pointFs = mutableListOf<PointF>()

                    list.forEachIndexed { index, item ->
                        val dtText = simpleDateFormat.format(item.dtInMilliseconds)
                        val tempText = "${item.temp.roundToInt()}${DEGREE_SIGN}"
                        val humidityText = "${item.humidity}"

                        val pointF = PointF(
                            size.width / visibleItemCount.float * index.float,
                            12.dp.toPx()
                        )

                        drawContext.canvas.nativeCanvas.drawText(dtText, pointF.x, pointF.y, dtPaint)

                        pointF.y += 24.dp.toPx()

                        drawContext.canvas.nativeCanvas.drawText(tempText, pointF.x, pointF.y, tempPaint)

                        pointF.y += 24.dp.toPx()
                        pointF.y += 1.0F.minus(item.temp.div(maxTemp)) * 48.dp.toPx()

                        pointFs.add(index, PointF(pointF.x, pointF.y))

                        drawCircle(
                            color = Color.Cyan,
                            radius = 8.0F,
                            center = Offset(pointF.x, pointF.y)
                        )

                        pointF.y -= 1.0F.minus(item.temp.div(maxTemp)) * 48.dp.toPx()
                        pointF.y += 24.dp.toPx()

                        waterDrop?.asImageBitmap()?.let {
                            val x = pointF.x.minus(it.width.half)
                            val y = pointF.y

                            drawImage(
                                image = it,
                                topLeft = Offset(x, y),
                                colorFilter = ColorFilter.tint(Color.Cyan, BlendMode.SrcAtop)
                            )

                            val right = pointF.x + it.width
                            val percentage = 1.0F - item.humidity.div(100.0F)
                            val bottom = pointF.y + percentage.times(it.height)

                            clipRect(
                                x,
                                y,
                                right,
                                bottom,
                                ClipOp.Intersect
                            ) {
                                drawImage(
                                    image = it,
                                    topLeft = Offset(x, pointF.y),
                                    colorFilter = ColorFilter.tint(Color.White, BlendMode.SrcAtop)
                                )
                            }

                            pointF.y += it.height
                        }

                        pointF.y += 16.dp.toPx()

                        drawContext.canvas.nativeCanvas.drawText(humidityText, pointF.x, pointF.y, humidityPaint)
                    }

                    val path = Path().apply {
                        reset()
                        moveTo(pointFs.first().x, pointFs.first().y)
                        pointFs.forEach {
                            lineTo(it.x, it.y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color.Blue,
                        style = Stroke(
                            width = 8.0F,
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    }
}