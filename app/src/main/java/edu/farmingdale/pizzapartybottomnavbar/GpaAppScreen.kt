package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun GpaAppScreen() {
    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.Cyan) } // Set initial background color to Cyan
    var btnLabel by remember { mutableStateOf("Compute GPA") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backColor), // Set background color to Cyan
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textFieldModifier = Modifier
            .padding(16.dp)
            .background(Color.Cyan) // Set TextField background color to Cyan
            .border(1.dp, Color.Black, shape = MaterialTheme.shapes.small.copy(CornerSize(8.dp)))

        TextField(
            value = grade1,
            onValueChange = { grade1 = it },
            modifier = textFieldModifier,
            label = { Text("Course 1 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to Number
        )

        TextField(
            value = grade2,
            onValueChange = { grade2 = it },
            modifier = textFieldModifier,
            label = { Text("Course 2 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to Number
        )

        TextField(
            value = grade3,
            onValueChange = { grade3 = it },
            modifier = textFieldModifier,
            label = { Text("Course 3 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to Number
        )

        Button(onClick = {
            if (btnLabel == "Compute GPA") {
                val gpaVal = calGPA(grade1, grade2, grade3)
                if (gpaVal != null) {
                    gpa = gpaVal.toString()
                    backColor = when {
                        gpaVal < 60 -> Color.Red
                        gpaVal in 60.0..79.0 -> Color.Yellow
                        else -> Color.Green
                    }
                    btnLabel = "Clear"
                } else {
                    gpa = "Invalid input"
                }
            } else {
                grade1 = ""
                grade2 = ""
                grade3 = ""
                gpa = ""
                backColor = Color.Cyan // Reset background color to Cyan
                btnLabel = "Compute GPA"
            }
        }, modifier = Modifier.padding(top = 56.dp)) {
            Text(btnLabel)
        }

        if (gpa.isNotEmpty()) {
            Text(text = "GPA: $gpa")
        }
    }
}

fun calGPA(grade1: String, grade2: String, grade3: String): Double {
    val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
    return grades.average()
}
