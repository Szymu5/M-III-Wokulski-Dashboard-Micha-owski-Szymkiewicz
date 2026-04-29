package com.example.wokulskidashboard.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.wokulskidashboard.model.Transaction

@Composable
fun IncomeForm(dodajPrzychod: (Transaction) -> Unit) {

    val nazwaPole = remember { mutableStateOf("") }
    val kwotaPole = remember { mutableStateOf("") }

    Column {
        Text("Dodaj przychód")

        OutlinedTextField(
            value = nazwaPole.value,
            onValueChange = { nazwaPole.value = it },
            label = { Text("Co sprzedano?") }
        )

        OutlinedTextField(
            value = kwotaPole.value,
            onValueChange = { kwotaPole.value = it },
            label = { Text("Za ile rubli?") }
        )

        Button(onClick = {

            val sprawdzonaKwota = kwotaPole.value.toDoubleOrNull()


            if (nazwaPole.value != "" && sprawdzonaKwota != null) {

                val nowaTransakcja = Transaction(nazwaPole.value, sprawdzonaKwota, false)


                dodajPrzychod(nowaTransakcja)


                nazwaPole.value = ""
                kwotaPole.value = ""
            }
        }) {
            Text("Zaksięguj")
        }
    }
}