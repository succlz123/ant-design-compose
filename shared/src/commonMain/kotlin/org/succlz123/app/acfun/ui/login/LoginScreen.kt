package org.succlz123.app.acfun.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.succlz123.ant.component.button.*
import org.succlz123.ant.component.checkbox.AntCheckboxOption
import org.succlz123.ant.component.checkbox.AntCheckboxSample
import org.succlz123.ant.component.radio.AntRadioOption
import org.succlz123.ant.component.radio.AntRadioSample
import org.succlz123.ant.theme.MacTheme
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.viewModel

@Composable
fun LoginScreen() {
    val loginVm = viewModel(LoginViewModel::class) {
        LoginViewModel()
    }
    val screenNavigator = LocalScreenNavigator.current

    Row(Modifier.fillMaxSize()) {
        Column(
            Modifier.weight(1f)
                .fillMaxHeight()
                .background(color = Color(red = 246, green = 246, blue = 246, alpha = 255)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            MacTheme {
                Text("Ant Design Component List")
                AntExampleView()
//                val menuItems = listOf(
//                    "Some options",
//                    "Orange",
//                    "Yellow",
//                    "Green",
//                    "Blue",
//                    "Indigo",
//                    "Kinda brownish gray",
//                )
//                var selectedIndex1 by remember { mutableStateOf(0) }
//                MacDropdownMenu1(
//                    menuItems,
//                    selectedIndex1,
//                    onItemSelected = {
//                        selectedIndex1 = it
//                    },
//                )
//
//                Spacer(Modifier.height(8.dp))
//
//                var selectedIndex2 by remember { mutableStateOf(0) }
//                MacDropdownMenu1(
//                    menuItems,
//                    selectedIndex2,
//                    onItemSelected = {
//                        selectedIndex2 = it
//                    },
//                )
//
//                Spacer(Modifier.height(8.dp))
            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AntExampleView() {
    MacTheme {
        Column(
            Modifier.padding(16.dp).fillMaxWidth().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            MacSearchField(
//                onSearchRequested = { println("Searched: $it") },
//                modifier = Modifier.width(200.dp)
//            )
//
//            Spacer(Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                Column(
//                    modifier = Modifier.width(180.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    var textFieldValue by remember { mutableStateOf("") }
//                    MacOutlinedTextField(
//                        textFieldValue,
//                        { textFieldValue = it },
//                        Modifier.fillMaxWidth().background(color = MaterialTheme.colors.surface),
//                        placeholder = { Text("Email") },
//                        singleLine = true
//                    )
//
//                    var textFieldValue2 by remember { mutableStateOf("An imaginary form") }
//                    MacOutlinedTextField(
//                        textFieldValue2,
//                        { textFieldValue2 = it },
//                        Modifier.fillMaxWidth().background(color = MaterialTheme.colors.surface),
//                        placeholder = { Text("Phone No.") },
//                        singleLine = true
//                    )
//                }
                Spacer(Modifier.height(16.dp))

                Text("Button Component")
                Spacer(Modifier.height(16.dp))
                ButtonsView()
                Spacer(Modifier.height(16.dp))

                Text("RadioGroup Component")
                Spacer(Modifier.height(16.dp))
                RadioGroupView()
                Spacer(Modifier.height(16.dp))

                Text("Checkbox Component")
                Spacer(Modifier.height(16.dp))
                CheckboxView()
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RadioGroupView() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val radioList: MutableState<ImmutableCollection<AntRadioOption>> = remember {
            mutableStateOf(
                persistentListOf(
                    AntRadioOption(value = "Apple"),
                    AntRadioOption(value = "Pear", enabled = false),
                    AntRadioOption(value = "Orange", checked = true)
                )
            )
        }
        Row {
            AntRadioSample(radioList)
            Spacer(modifier = Modifier.width(16.dp))
            AntRadioSample(radioList.value, onChange = { index, option ->
                radioList.value = radioList.value.map { it.copy(checked = (it == option)) }.toImmutableList()
            })
        }
        AntPrimaryButton(onClick = {
            radioList.value = radioList.value.map { it.copy(checked = !it.checked) }.toImmutableList()
        }) {
            Text("Toggle checked")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ButtonsView() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        AntPrimaryButton(onClick = {}) {
            Text("Primary")
        }
        AntDefaultButton(onClick = {}) {
            Text("Default")
        }
        AntPrimaryButton(enabled = false, onClick = {}) {
            Text("Disabled")
        }
        AntDashedButton(onClick = {}) {
            Text("Dashed")
        }
        AntTextButton(onClick = {}) {
            Text("Text")
        }
        AntLinkButton(onClick = {}) {
            Text("Link")
        }
    }
}

@Composable
private fun CheckboxView() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val radioList: MutableState<ImmutableCollection<AntCheckboxOption>> = remember {
            mutableStateOf(
                persistentListOf(
                    AntCheckboxOption(value = "Apple", checked = true),
                    AntCheckboxOption(value = "Pear", enabled = true),
                    AntCheckboxOption(value = "Watermelon", enabled = true),
                    AntCheckboxOption(value = "Orange", checked = true, enabled = false)
                )
            )
        }
        AntCheckboxSample(radioList)
    }
}
