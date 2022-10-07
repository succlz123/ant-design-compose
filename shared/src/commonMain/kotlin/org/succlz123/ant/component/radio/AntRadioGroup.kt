package org.succlz123.ant.component.radio

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.toImmutableList

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
fun AntRadioGroup(
    items: MutableCollection<AntRadioOption>,
    onChange: ((Int, AntRadioOption) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    var options by remember {
        mutableStateOf(items)
    }
    LaunchedEffect(items) {
        options = items
    }
    AntRadioGroup(options.toImmutableList(), onChange = { index, option ->
        options = options.map { it.copy(checked = (it == option)) }.toMutableList()
        onChange?.invoke(index, option)
    }, modifier)
}

@Composable
fun AntRadioGroup(
    items: ImmutableCollection<AntRadioOption>,
    onChange: ((Int, AntRadioOption) -> Unit),
    modifier: Modifier = Modifier,
) {
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEachIndexed { index, option ->
            val interactionSource = remember { MutableInteractionSource() }
            Row(
                Modifier.selectable(
                    selected = (option == items.filter { it.enabled }.find { it.checked }),
                    onClick = {
                        if (option.enabled) {
//                            for (item in items) {
//                                item.checked = (item == option)
//                            }
//                            selectedOption = option
                            onChange.invoke(index, option)
                        }
                    },
                    role = Role.RadioButton,
                    interactionSource = interactionSource,
                    indication = null,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AntRadioButtonWithLabel(option, interactionSource = interactionSource)
            }
        }
    }
}

@Composable
private fun AntRadioButtonWithLabel(option: AntRadioOption, interactionSource: MutableInteractionSource? = null) {
    Row(
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