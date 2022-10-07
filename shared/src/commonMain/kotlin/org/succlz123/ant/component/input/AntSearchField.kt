package org.succlz123.ant.component.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Composable
fun MacSearchField(
    onSearchRequested: (query: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }
    MacOutlinedTextField(
        value,
        onValueChange = { value = it },
        // Not sure this is the best way to handle enter pressed. IME actions don't seem reliable yet on desktop
        modifier = modifier.height(26.dp)
            .onKeyEvent {
                if (it.type == KeyEventType.KeyUp && it.key == Key.Enter) {
                    onSearchRequested(value)
                    return@onKeyEvent true
                }
                false
            },
        leadingIcon = { Text("􀊫") },
        trailingIcon = {
            if (value.isNotEmpty()) {
                CloseIcon(
                    closeClicked = {
                        value = ""
                        onSearchRequested(value)
                    }
                )
            }
        },
        placeholder = { Text("Search") },
        singleLine = true,
        cornerRadius = SearchTextFieldCornerRadius
    )
}

@Composable
private fun CloseIcon(closeClicked: () -> Unit) {
    Text(
        "删除",
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            closeClicked()
        },
        fontSize = 10.sp,
        fontWeight = FontWeight.W700
    )
}

private val SearchTextFieldCornerRadius = 8.dp