package org.succlz123.ant.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.succlz123.ant.theme.AntDisabledBackgroundColor
import org.succlz123.ant.theme.AntDisabledContentColor

@ExperimentalMaterialApi
@Composable
fun AntPrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    antButtonStyle: AntButtonStyle = AntButtonStyle.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ZeroButtonElevation,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        disabledBackgroundColor = AntDisabledBackgroundColor,
        disabledContentColor = AntDisabledContentColor
    ),
    contentPadding: PaddingValues = AntButtonPaddingValues(antButtonStyle),
    content: @Composable RowScope.() -> Unit
) {
    BaseAntButton(
        onClick = onClick,
        modifier = modifier.sizeIn(antButtonStyle.minWidth, antButtonStyle.minHeight),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        antButtonStyle = antButtonStyle,
        border = border,
        dash = false,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@ExperimentalMaterialApi
@Composable
fun AntDefaultButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    antButtonStyle: AntButtonStyle = AntButtonStyle.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = AntButtonDefaults.defaultButtonElevation(),
    border: BorderStroke? = null,
    colors: ButtonColors = AntButtonDefaults.secondaryButtonColors(),
    contentPadding: PaddingValues = AntButtonPaddingValues(antButtonStyle),
    content: @Composable RowScope.() -> Unit
) {
    BaseAntButton(
        onClick = onClick,
        modifier = modifier.sizeIn(antButtonStyle.minWidth, antButtonStyle.minHeight),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        antButtonStyle = antButtonStyle,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@ExperimentalMaterialApi
@Composable
fun AntDashedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    antButtonStyle: AntButtonStyle = AntButtonStyle.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ZeroButtonElevation,
    border: BorderStroke? = null,
    colors: ButtonColors = AntButtonDefaults.dashedButtonColors(),
    contentPadding: PaddingValues = AntButtonPaddingValues(antButtonStyle),
    content: @Composable() (RowScope.() -> Unit)
) {
    BaseAntButton(
        onClick = onClick,
        modifier = modifier.sizeIn(antButtonStyle.minWidth, antButtonStyle.minHeight),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        antButtonStyle = antButtonStyle,
        border = border,
        dash = true,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@ExperimentalMaterialApi
@Composable
fun AntTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    antButtonStyle: AntButtonStyle = AntButtonStyle.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ZeroButtonElevation,
    border: BorderStroke? = null,
    colors: ButtonColors = AntButtonDefaults.textButtonColors(),
    contentPadding: PaddingValues = AntButtonPaddingValues(antButtonStyle),
    content: @Composable() (RowScope.() -> Unit)
) {
    BaseAntButton(
        onClick = onClick,
        modifier = modifier.sizeIn(antButtonStyle.minWidth, antButtonStyle.minHeight),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        antButtonStyle = antButtonStyle,
        border = border,
        dash = false,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@ExperimentalMaterialApi
@Composable
fun AntLinkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    antButtonStyle: AntButtonStyle = AntButtonStyle.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ZeroButtonElevation,
    border: BorderStroke? = null,
    colors: ButtonColors = AntButtonDefaults.linkButtonColors(),
    contentPadding: PaddingValues = AntButtonPaddingValues(antButtonStyle),
    content: @Composable() (RowScope.() -> Unit)
) {
    BaseAntButton(
        onClick = onClick,
        modifier = modifier.sizeIn(antButtonStyle.minWidth, antButtonStyle.minHeight),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        antButtonStyle = antButtonStyle,
        border = border,
        dash = false,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@Composable
fun AntButtonPaddingValues(antButtonStyle: AntButtonStyle): PaddingValues {
    return if (antButtonStyle == AntButtonStyle.Small) {
        PaddingValues(10.dp, 2.dp, 10.dp, 2.dp)
    } else {
        PaddingValues(10.dp, 6.dp, 10.dp, 6.dp)
    }
}

sealed class AntButtonStyle(val minWidth: Dp, val minHeight: Dp) {
    object Large : AntButtonStyle(minWidth = 157.dp, minHeight = 28.dp)
    object Default : AntButtonStyle(minWidth = 157.dp, minHeight = 28.dp)
    object Small : AntButtonStyle(minWidth = Dp.Unspecified, 20.dp)
}

object ZeroButtonElevation : ButtonElevation {

    @Composable
    override fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp> {
        return mutableStateOf(0.dp)
    }
}

object AntButtonDefaults {

    @Composable
    fun secondaryButtonColors(
        backgroundColor: Color = MaterialTheme.colors.surface,
        contentColor: Color = MaterialTheme.colors.onSurface,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledBackgroundColor = AntDisabledBackgroundColor,
        contentColor = contentColor,
        disabledContentColor = AntDisabledContentColor
    )

    @Composable
    fun defaultButtonElevation() = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 2.dp,
        disabledElevation = 0.dp
    )

    @Composable
    fun dashedButtonColors(
        backgroundColor: Color = MaterialTheme.colors.surface,
        contentColor: Color = MaterialTheme.colors.onSurface,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledBackgroundColor = AntDisabledBackgroundColor,
        contentColor = contentColor,
        disabledContentColor = AntDisabledContentColor
    )

    @Composable
    fun textButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colors.onSurface,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledBackgroundColor = AntDisabledBackgroundColor,
        contentColor = contentColor,
        disabledContentColor = AntDisabledContentColor
    )

    @Composable
    fun linkButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colors.primary,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        disabledBackgroundColor = AntDisabledBackgroundColor,
        contentColor = contentColor,
        disabledContentColor = AntDisabledContentColor
    )
}