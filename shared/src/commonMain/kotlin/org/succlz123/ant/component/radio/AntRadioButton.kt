package org.succlz123.ant.component.radio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.ant.theme.AntDisabledBackgroundColor
import org.succlz123.ant.theme.AntTheme

val RadioTextSize = 13.sp
val RadioSize = 16.dp
val DotRadius = 3.dp
val BorderStrokeWidth = 1.dp

data class AntRadioOption(
    val value: String,
    val checked: Boolean = false,
    val enabled: Boolean = true,

    val imgSize: Dp = RadioSize,
    val textSize: TextUnit = RadioTextSize,
    val textColor: @Composable () -> Color = {
        LocalTextStyle.current.color
    },
    val textPaddingStart: Dp = 6.dp
)

@Composable
fun AntRadioButtonWithLabel(
    option: AntRadioOption,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AntRadioButton(option.checked,
            onClick = null,
            enabled = option.enabled,
            interactionSource = interactionSource ?: remember { MutableInteractionSource() })
        Spacer(Modifier.width(option.textPaddingStart))
        Text(
            option.value, color = if (option.enabled) {
                option.textColor.invoke()
            } else {
                option.textColor.invoke().copy(alpha = ContentAlpha.disabled)
            }, fontSize = option.textSize
        )
    }
}

@Composable
fun AntRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: AntRadioButtonColors = AntRadioButtonColors()
) {
    val state = ToggleableState(selected)
    val backgroundColor = MaterialTheme.colors.surface

    val selectableModifier = if (onClick != null) {
        Modifier.selectable(
            selected = selected,
            enabled = enabled,
            role = Role.RadioButton,
            interactionSource = interactionSource,
            indication = if (enabled) LocalIndication.current else null, // Unfortunately necessary
            onClick = onClick,
        )
    } else {
        Modifier.indication(
            interactionSource = interactionSource,
            indication = if (enabled) LocalIndication.current else null,
        )
    }
    Canvas(
        modifier.requiredSize(RadioSize).clip(CircleShape).then(selectableModifier).wrapContentSize(Alignment.Center)
    ) {
        val borderColor = colors.borderColor(enabled, state)
        val radioColor = colors.radioColor(enabled, state)
        val dotColor = colors.centerDotColor(enabled, state)

        drawRadio(
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            radioColor = radioColor,
            dotColor = dotColor,
            showDot = state != ToggleableState.Off
        )
    }
}

private fun DrawScope.drawRadio(
    backgroundColor: Color, borderColor: Color, radioColor: Color, dotColor: Color, showDot: Boolean
) {
    val borderStrokeWidth = BorderStrokeWidth.toPx()

    // Background
    drawCircle(
        color = backgroundColor, radius = RadioSize.toPx() / 2, style = Fill
    )

    // Filled radio button
    drawCircle(
        color = radioColor, radius = RadioSize.toPx() / 2, style = Fill
    )

    // Border
    drawCircle(
        color = borderColor, radius = RadioSize.toPx() / 2 - borderStrokeWidth, style = Stroke(borderStrokeWidth)
    )

    // Inner dot
    if (showDot) {
        drawCircle(dotColor, DotRadius.toPx(), style = Fill)
    }
}

@Composable
private fun AntRadioButtonColors(
    selectedRadioColor: Color = AntTheme.colors.primary,
    selectedCenterDotColor: Color = MaterialTheme.colors.surface,
    indeterminateCenterDotColor: Color = MaterialTheme.colors.onSurface,
    disabledCenterDotColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
    disabledRadioColor: Color = AntDisabledBackgroundColor.copy(alpha = 0.03f),
    borderColor: Color = AntTheme.colors.borderDark,
): AntRadioButtonColors {
    return remember(
        selectedRadioColor,
        selectedCenterDotColor,
        indeterminateCenterDotColor,
        disabledCenterDotColor,
        disabledRadioColor,
    ) {
        AntRadioButtonColorsImpl(
            selectedCenterDotColor = selectedCenterDotColor,
            unselectedCenterDotColor = selectedCenterDotColor.copy(alpha = 0f),
            indeterminateCenterDotColor = indeterminateCenterDotColor.copy(alpha = 0f),
            selectedRadioColor = selectedRadioColor,
            unselectedRadioColor = selectedRadioColor.copy(alpha = 0f),
            disabledCenterDotColor = disabledCenterDotColor,
            disabledRadioColor = disabledRadioColor,
            unselectedBorderColor = borderColor,
            disabledBorderColor = borderColor.copy(alpha = 0.8f),
        )
    }
}

private class AntRadioButtonColorsImpl(
    private val selectedCenterDotColor: Color,
    private val unselectedCenterDotColor: Color,
    private val indeterminateCenterDotColor: Color,
    private val selectedRadioColor: Color,
    private val unselectedRadioColor: Color,
    private val disabledCenterDotColor: Color,
    private val disabledRadioColor: Color,
    private val unselectedBorderColor: Color,
    private val disabledBorderColor: Color,
) : AntRadioButtonColors {
    override fun borderColor(enabled: Boolean, state: ToggleableState): Color {
        return if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> Color.Transparent
                ToggleableState.Off -> unselectedBorderColor
            }
        } else {
            disabledBorderColor
        }
    }

    override fun radioColor(enabled: Boolean, state: ToggleableState): Color {
        return if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> selectedRadioColor
                ToggleableState.Off -> unselectedRadioColor
            }
        } else {
            disabledRadioColor
        }
    }

    override fun centerDotColor(enabled: Boolean, state: ToggleableState): Color {
        return if (enabled) {
            when (state) {
                ToggleableState.Off -> unselectedCenterDotColor
                ToggleableState.On -> selectedCenterDotColor
                ToggleableState.Indeterminate -> indeterminateCenterDotColor
            }
        } else {
            when (state) {
                ToggleableState.Off -> unselectedCenterDotColor
                ToggleableState.On -> disabledCenterDotColor
                ToggleableState.Indeterminate -> disabledCenterDotColor
            }
        }
    }
}

/**
 * Copies [CheckboxColors] but with the addition of passing in the enabled state to calculate checkbox color
 */
interface AntRadioButtonColors {

    /**
     * Represents the color used for the checkmark inside the checkbox, depending on [state].
     *
     * @param state the [ToggleableState] of the checkbox
     */
    fun centerDotColor(enabled: Boolean, state: ToggleableState): Color

    /**
     * Represents the color used for the box (background) of the checkbox, depending on [enabled]
     * and [state].
     *
     * @param enabled whether the checkbox is enabled or not
     * @param state the [ToggleableState] of the checkbox
     */
    fun radioColor(enabled: Boolean, state: ToggleableState): Color

    /**
     * Represents the color used for the border of the checkbox, depending on [enabled] and [state].
     *
     * @param enabled whether the checkbox is enabled or not
     * @param state the [ToggleableState] of the checkbox
     */
    fun borderColor(enabled: Boolean, state: ToggleableState): Color
}
