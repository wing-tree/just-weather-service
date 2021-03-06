package com.wing.tree.just.weather.service.ui.view.main.component

import android.graphics.Paint
import android.graphics.PointF
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.wing.tree.just.weather.service.R
import com.wing.tree.just.weather.service.constant.DEGREE_SIGN
import com.wing.tree.just.weather.service.constant.TWO
import com.wing.tree.just.weather.service.constant.icons
import com.wing.tree.just.weather.service.domain.model.local.openweather.Forecast
import com.wing.tree.just.weather.service.extension.float
import com.wing.tree.just.weather.service.extension.half
import com.wing.tree.just.weather.service.ui.view.main.ForecastState
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

private const val VISIBLE_ITEM_COUNT = 5

@Composable
fun Forecast(
    modifier: Modifier,
    uiState: ForecastState
) {
    when(uiState) {
        is ForecastState.Loading -> Unit
        is ForecastState.Content -> {
            uiState.forecast?.let { forecast ->
                val group = forecast.list.groupBy { it.dayOfWeek }

                Column(modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ) {
                    group.values.forEachIndexed { index, list ->
                        if (list.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                ForecastContent(
                                    modifier = Modifier.fillMaxWidth(),
                                    list = list
                                )
                            }
                        }
                    }
                }
            }
        }
        is ForecastState.Error -> Unit
    }
}

@Composable
private fun ForecastContent(
    modifier: Modifier,
    list: List<Forecast.Item>,
    visibleItemCount: Int = VISIBLE_ITEM_COUNT
) {
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
            typeface = ResourcesCompat.getFont(context, R.font.noto_serif_korean_regular)
        }
    }

    val tempPaint = remember(localDensity) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = localDensity.run { 14.dp.toPx() }
            typeface = ResourcesCompat.getFont(context, R.font.noto_serif_korean_medium)
        }
    }

    val humidityPaint = remember(localDensity) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = localDensity.run { 12.dp.toPx() }
            typeface = ResourcesCompat.getFont(context, R.font.noto_serif_korean_regular)
        }
    }

    Surface(
        modifier = modifier.onSizeChanged { size = it },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            Modifier
                .height(192.dp)
                .background(Color.White)
                .horizontalScroll(rememberScrollState())
        ) {
            val spacing = size.width.div(visibleItemCount.float)
            val waterDrop = AppCompatResources.getDrawable(context, R.drawable.ic_round_water_drop_16)?.toBitmap()
            val width = with(localDensity) { spacing.toDp() } * list.size.dec()
            val horizontal = with(localDensity) { spacing.half.toDp() }

            Box(
                modifier = modifier.width(width.plus(horizontal.times(TWO))),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal, 0.dp)
                ) {
                    val maxTemp = list.maxOfOrNull { it.temp } ?: 1.0F

                    val pointFs = mutableListOf<PointF>()

                    list.forEachIndexed { index, item ->
                        val dtText = simpleDateFormat.format(item.dtInMilliseconds)
                        val tempText = "${item.temp.roundToInt()}${DEGREE_SIGN}"
                        val humidityText = "${item.humidity}%"

                        val pointF = PointF(
                            spacing * index.float,
                            12.dp.toPx()
                        )

                        drawContext.canvas.nativeCanvas.drawText(dtText, pointF.x, pointF.y, dtPaint)

                        pointF.y += 8.dp.toPx()

                        icons[item.weather?.icon]?.let {
                            val image = ImageBitmap.imageResource(context.resources, id = it)

                            drawImage(
                                image = image,
                                topLeft = Offset(pointF.x - image.width.half, pointF.y)
                            )

                            pointF.y += image.height
                        }

                        pointF.y += 16.dp.toPx()

                        drawContext.canvas.nativeCanvas.drawText(tempText, pointF.x, pointF.y, tempPaint)

                        pointF.y += 40.dp.toPx()
                        pointF.y += 1.0F.minus(item.temp.div(maxTemp)) * 48.dp.toPx()

                        pointFs.add(index, PointF(pointF.x, pointF.y))

                        drawCircle(
                            color = Color.Cyan,
                            radius = 8.0F,
                            center = Offset(pointF.x, pointF.y)
                        )

                        pointF.y -= 1.0F.minus(item.temp.div(maxTemp)) * 48.dp.toPx()
                        pointF.y += 40.dp.toPx()

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

                            clipRect(x, y, right, bottom, ClipOp.Intersect) {
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