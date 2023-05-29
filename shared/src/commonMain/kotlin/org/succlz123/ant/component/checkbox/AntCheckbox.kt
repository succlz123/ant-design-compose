package org.succlz123.ant.component.checkbox

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.ant.component.radio.*
import org.succlz123.ant.theme.AntDisabledBackgroundColor
import org.succlz123.ant.theme.AntTheme

private val ANT_CHECKBOX_RADIUS_SIZE = 2.dp
private val ANT_CHECKBOX_STROKE_WIDTH = 1.dp
private val ANT_CHECKBOX_SIZE = 14.dp
private val ANT_CHECK_BOX_TEXT_SIZE = 13.sp

data class AntCheckboxOption(
    val value: String,
    val checked: Boolean = false,
    val enabled: Boolean = true,

    val boxSize: Dp = ANT_CHECKBOX_SIZE,
    val radiusSize: Dp = ANT_CHECKBOX_RADIUS_SIZE,
    val strokeWidth: Dp = ANT_CHECKBOX_STROKE_WIDTH,
    val textSize: TextUnit = ANT_CHECK_BOX_TEXT_SIZE,
    val textColor: @Composable () -> Color = {
        LocalTextStyle.current.color
    },
    val textPaddingStart: Dp = 6.dp
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AntCheckboxWithLabel(option: AntCheckboxOption) {
    var isChecked by remember { mutableStateOf(option.checked) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        Modifier.clickable(interactionSource = interactionSource, indication = null) {
            if (!option.enabled) {
                return@clickable
            }
            isChecked = !isChecked
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AntCheckbox(
            isChecked,
            option,
            { isChecked = it },
            enabled = option.enabled,
            interactionSource = interactionSource
        )
        Spacer(Modifier.width(option.textPaddingStart))
        Text(
            option.value,
            color = if (option.enabled) {
                LocalTextStyle.current.color
            } else {
                LocalTextStyle.current.color.copy(alpha = ContentAlpha.disabled)
            },
            fontSize = option.textSize
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun AntCheckbox(
    checked: Boolean,
    option: AntCheckboxOption,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: AntCheckboxColors = antCheckboxColors()
) {
    Box(
        modifier = modifier.size(option.boxSize)
            .clip(RoundedCornerShape(option.radiusSize))
            .background(color = MaterialTheme.colors.surface)
            .triStateToggleable(
                state = ToggleableState(checked),
                onClick = { onCheckedChange(!checked) },
                enabled = enabled,
                interactionSource = interactionSource,
                indication = if (enabled) LocalIndication.current else null // Unfortunately necessary
            ),
        Alignment.Center
    ) {
        val state = ToggleableState(checked)
        Canvas(
            modifier.wrapContentSize(Alignment.Center).fillMaxSize()
        ) {
            val boxColor = colors.boxColor(enabled, state)
            val borderColor = colors.borderColor(enabled, state)
            val strokeWidthPx = option.strokeWidth.toPx()
            drawBox(
                boxColor = boxColor,
                borderColor = borderColor,
                radius = option.radiusSize.toPx(),
                strokeWidth = strokeWidthPx
            )
        }
        val checkColor = colors.checkmarkColor(enabled, state)
        Icon(
            Icons.Sharp.Done,
            modifier = Modifier.size(12.dp),
            contentDescription = "Right",
            tint = checkColor,
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun antCheckboxColors(
    checkedColor: Color = AntTheme.colors.primary,
    borderColor: Color = AntTheme.colors.borderDark,
    checkmarkColor: Color = MaterialTheme.colors.surface,
    disabledCheckmarkColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
    disabledColor: Color = AntDisabledBackgroundColor.copy(alpha = 0.03f),
    disabledIndeterminateColor: Color = checkedColor.copy(alpha = ContentAlpha.disabled)
): AntCheckboxColors {
    return remember(
        checkedColor,
        borderColor,
        checkmarkColor,
        disabledColor,
        disabledIndeterminateColor
    ) {
        AntCheckboxColorsImpl(
            checkedCheckmarkColor = checkmarkColor,
            uncheckedCheckmarkColor = checkmarkColor.copy(alpha = 0f),
            checkedBoxColor = checkedColor,
            uncheckedBoxColor = checkedColor.copy(alpha = 0f),
            disabledCheckmarkColor = disabledCheckmarkColor,
            disabledCheckedBoxColor = disabledColor,
            disabledUncheckedBoxColor = disabledColor,
            disabledIndeterminateBoxColor = disabledIndeterminateColor,
            checkedBorderColor = checkedColor,
            uncheckedBorderColor = borderColor,
            disabledBorderColor = borderColor.copy(alpha = 0.8f),
            disabledIndeterminateBorderColor = disabledIndeterminateColor
        )
    }
}

@ExperimentalMaterialApi
private class AntCheckboxColorsImpl(
    private val checkedCheckmarkColor: Color,
    private val uncheckedCheckmarkColor: Color,
    private val checkedBoxColor: Color,
    private val uncheckedBoxColor: Color,
    private val disabledCheckmarkColor: Color,
    private val disabledCheckedBoxColor: Color,
    private val disabledUncheckedBoxColor: Color,
    private val disabledIndeterminateBoxColor: Color,
    private val checkedBorderColor: Color,
    private val uncheckedBorderColor: Color,
    private val disabledBorderColor: Color,
    private val disabledIndeterminateBorderColor: Color
) : AntCheckboxColors {
    override fun borderColor(enabled: Boolean, state: ToggleableState): Color {
        return if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> checkedBorderColor
                ToggleableState.Off -> uncheckedBorderColor
            }
        } else {
            when (state) {
                ToggleableState.Indeterminate -> disabledIndeterminateBorderColor
                ToggleableState.On, ToggleableState.Off -> disabledBorderColor
            }
        }
    }

    override fun boxColor(enabled: Boolean, state: ToggleableState): Color {
        return if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> checkedBoxColor
                ToggleableState.Off -> uncheckedBoxColor
            }
        } else {
            when (state) {
                ToggleableState.On -> disabledCheckedBoxColor
                ToggleableState.Indeterminate -> disabledIndeterminateBoxColor
                ToggleableState.Off -> disabledUncheckedBoxColor
            }
        }
    }

    override fun checkmarkColor(enabled: Boolean, state: ToggleableState): Color {
        return if (enabled) {
            if (state == ToggleableState.Off) {
                uncheckedCheckmarkColor
            } else {
                checkedCheckmarkColor
            }
        } else {
            if (state == ToggleableState.Off) {
                uncheckedCheckmarkColor
            } else {
                disabledCheckmarkColor
            }
        }
    }
}

private fun DrawScope.drawBox(
    boxColor: Color,
    borderColor: Color,
    radius: Float,
    strokeWidth: Float
) {
    val halfStrokeWidth = strokeWidth / 2.0f
    val stroke = Stroke(strokeWidth)
    val checkboxSize = size.width
    drawRoundRect(
        boxColor,
        topLeft = Offset(strokeWidth, strokeWidth),
        size = Size(checkboxSize - strokeWidth * 2, checkboxSize - strokeWidth * 2),
        cornerRadius = CornerRadius(radius / 2),
        style = Fill
    )
    drawRoundRect(
        borderColor,
        topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
        size = Size(checkboxSize - strokeWidth, checkboxSize - strokeWidth),
        cornerRadius = CornerRadius(radius),
        style = stroke
    )
}


/**
 * Copies [CheckboxColors] but with the addition of passing in the enabled state to calculate checkbox color
 */
@ExperimentalMaterialApi
@Stable
interface AntCheckboxColors {

    /**
     * Represents the color used for the checkmark inside the checkbox, depending on [state].
     *
     * @param state the [ToggleableState] of the checkbox
     */
    fun checkmarkColor(enabled: Boolean, state: ToggleableState): Color

    /**
     * Represents the color used for the box (background) of the checkbox, depending on [enabled]
     * and [state].
     *
     * @param enabled whether the checkbox is enabled or not
     * @param state the [ToggleableState] of the checkbox
     */
    fun boxColor(enabled: Boolean, state: ToggleableState): Color

    /**
     * Represents the color used for the border of the checkbox, depending on [enabled] and [state].
     *
     * @param enabled whether the checkbox is enabled or not
     * @param state the [ToggleableState] of the checkbox
     */
    fun borderColor(enabled: Boolean, state: ToggleableState): Color
}
