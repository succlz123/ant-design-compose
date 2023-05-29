package org.succlz123.ant.component.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.toImmutableList


@Composable
fun AntCheckboxSample(
    items: MutableState<ImmutableCollection<AntCheckboxOption>>,
    onChange: ((Int, AntCheckboxOption) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    AntCheckboxSample(items.value, onChange = { index, option ->
        items.value = items.value.map {
            it.copy(
                checked = if (it == option) {
                    !it.checked
                } else {
                    it.checked
                }
            )
        }.toImmutableList()
        onChange?.invoke(index, option)
    }, modifier)
}

@Composable
fun AntCheckboxSample(
    items: ImmutableCollection<AntCheckboxOption>,
    onChange: ((Int, AntCheckboxOption) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEachIndexed { index, option ->
            Box(
                modifier = Modifier.clickable {
                    onChange?.invoke(index, option)
                },
                contentAlignment = Alignment.Center,
            ) {
                AntCheckboxWithLabel(option)
            }
        }
    }
}
