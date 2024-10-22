import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlin.math.ceil


class PizzaPartyViewModel : ViewModel() {
    var totalPizzas by mutableStateOf(0)
        private set

    fun calculatePizzas(numPeople: Int, hungerLevel: String) {
        totalPizzas = calculateNumPizzas(numPeople, hungerLevel)
    }

    private fun calculateNumPizzas(numPeople: Int, hungerLevel: String): Int {
        val slicesPerPizza = 8
        val slicesPerPerson = when (hungerLevel) {
            "Light" -> 2
            "Medium" -> 3
            "Hungry" -> 4 // New hunger level
            "Very hungry" -> 5
            else -> 5 // Default to Very hungry
        }

        return ceil(numPeople * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
    }
}

@Composable
fun PizzaPartyScreen(modifier: Modifier = Modifier) {
    val viewModel: PizzaPartyViewModel = remember { PizzaPartyViewModel() }
    var numPeopleInput by remember { mutableStateOf("") }
    var hungerLevel by remember { mutableStateOf("Medium") }

    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Text(
            text = "Pizza Party",
            fontSize = 38.sp,
            modifier = modifier.padding(bottom = 16.dp)
        )
        NumberField(
            labelText = "Number of people?",
            textInput = numPeopleInput,
            onValueChange = { numPeopleInput = it },
            modifier = modifier.padding(bottom = 16.dp).fillMaxWidth()
        )
        RadioGroup(
            labelText = "How hungry?",
            radioOptions = listOf("Light", "Medium", "Hungry", "Very hungry"), // Added "Hungry"
            selectedOption = hungerLevel,
            onSelected = { hungerLevel = it },
            modifier = modifier
        )
        Text(
            text = "Total pizzas: ${viewModel.totalPizzas}",
            fontSize = 22.sp,
            modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Button(
            onClick = {
                // Ensure safe conversion to Int and call the ViewModel to calculate pizzas
                numPeopleInput.toIntOrNull()?.let { numPeople ->
                    viewModel.calculatePizzas(numPeople, hungerLevel)
                }
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }
    }
}

@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textInput,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )
}

@Composable
fun RadioGroup(
    labelText: String,
    radioOptions: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSelectedOption: (String) -> Boolean = { selectedOption == it }

    Column {
        Text(labelText)
        radioOptions.forEach { option ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = isSelectedOption(option),
                        onClick = { onSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedOption(option),
                    onClick = null,
                    modifier = modifier.padding(end = 8.dp)
                )
                Text(
                    text = option,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}
