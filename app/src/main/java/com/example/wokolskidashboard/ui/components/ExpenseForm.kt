package com.example.wokolskidashboard.ui.components

import android.view.SurfaceControl
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseForm(dodajWydatek: (SurfaceControl.Transaction) -> Unit) {
    val nazwaWydatek = remember { mutableStateOf("") }
    val kwotaWydatek = remember { mutableStateOf("") }

    val czyBladKwoty = kwotaWydatek.value.isNotEmpty() && kwotaWydatek.value.toDoubleOrNull() == null
    val czyPuste = nazwaWydatek.value.isEmpty() || kwotaWydatek.value.isEmpty()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Dodaj wydatek (Koszty):",
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nazwaWydatek.value,
            onValueChange = { nazwaWydatek.value = it },
            label = { Text("Na co poszły ruble?") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = kwotaWydatek.value,
            onValueChange = { kwotaWydatek.value = it },
            label = { Text("Ile wydano?") },
            modifier = Modifier.fillMaxWidth()
        )