package org.succlz123.ant.component.radio

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AntRadioSample(
    items: MutableState<ImmutableCollection<AntRadioOption>>,
    onChange: ((Int, AntRadioOption) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    AntRadioSample(items.value, onChange = { index, option ->
        items.value = items.value.map { it.copy(checked = (it == option)) }.toImmutableList()
        onChange?.invoke(index, option)
    }, modifier)
}

@Composable
fun AntRadioSample(
    items: ImmutableCollection<AntRadioOption>,
    onChange: ((Int, AntRadioOption) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEachIndexed { index, option ->
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                Modifier.selectable(
                    selected = (option == items.filter { it.enabled }.find { it.checked }),
                    onClick = {
                        if (option.enabled) {
                            onChange?.invoke(index, option)
                        }
                    },
                    role = Role.RadioButton,
                    interactionSource = interactionSource,
                    indication = null,
                ),
                contentAlignment = Alignment.Center,
            ) {
                AntRadioButtonWithLabel(option, interactionSource = interactionSource)
            }
        }
    }
}