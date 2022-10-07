package org.succlz123.ant.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@ExperimentalMaterialApi
@Composable
fun BaseAntButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    antButtonStyle: AntButtonStyle = AntButtonStyle.Default,
    border: BorderStroke? = null,
    dash: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by colors.contentColor(enabled)
    val density = LocalDensity.current
    val stroke = remember {
        Stroke(
            width = 3.dp.value,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.value, 6.dp.value), 0f)
        )
    }
    val radius = remember {
        when (antButtonStyle) {
            AntButtonStyle.Default -> {
                CornerRadius(4.dp.value * density.density)
            }

            AntButtonStyle.Large -> {
                CornerRadius(8.dp.value * density.density)

            }

            AntButtonStyle.Small -> {
                CornerRadius(4.dp.value * density.density)
            }
        }
    }
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = when (antButtonStyle) {
            AntButtonStyle.Default -> {
                RoundedCornerShape(4.dp)
            }

            AntButtonStyle.Large -> {
                RoundedCornerShape(8.dp)
            }

            AntButtonStyle.Small -> {
                RoundedCornerShape(4.dp)
            }
        },
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation.elevation(enabled, interactionSource).value,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier.defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth, minHeight = ButtonDefaults.MinHeight
                    ).drawBehind {
                        if (dash) {
                            drawRoundRect(
                                color = Color.LightGray, style = stroke, cornerRadius = radius
                            )
                        }
                    }.padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}