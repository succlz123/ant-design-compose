package org.succlz123.ant.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AntTheme {

    val colors: AntColors
        @Composable
        get() = AmbientAntColors.current

    val defaultColors
        @Composable
        get() = lightColors(
            primary = antLightPalette.primary,
        )

    val defaultTypography
        @Composable
        get() = Typography(
//            defaultFontFamily = MacFonts.SFPro(),
            button = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
            )
        )

    val defaultShapes
        @Composable
        get() = Shapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(11.dp),
            large = RoundedCornerShape(11.dp)
        )
}

private val AmbientAntColors = staticCompositionLocalOf<AntColors> {
    error("No MacColors provided")
}


/**
 * Wraps [MaterialTheme] with modifications to match MacOS Theme
 * */
@Composable
fun MacTheme(
    colors: Colors = AntTheme.defaultColors,
    typography: Typography = AntTheme.defaultTypography,
    shapes: Shapes = AntTheme.defaultShapes,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(AmbientAntColors provides antLightPalette) {
        MaterialTheme(colors, typography, shapes) {
            val indication = remember {
                MacIndication
            }
            CompositionLocalProvider(
                LocalIndication provides indication,
                content = content
            )
        }
    }
}

/**
 * Uses the default debug click indication (gray overlay) since this is exactly what Mac Theme does.
 * */
private object MacIndication : Indication {

    private class DefaultDebugIndicationInstance(
        private val isPressed: State<Boolean>
    ) : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawRect(color = Color.Black.copy(alpha = 0.07f), size = size)
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        return remember(interactionSource) {
            DefaultDebugIndicationInstance(isPressed)
        }
    }
}