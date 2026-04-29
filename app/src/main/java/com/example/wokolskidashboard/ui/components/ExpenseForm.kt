package com.example.wokolskidashboard.ui.components

import android.view.SurfaceControl
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ExpenseForm(dodajWydatek: (SurfaceControl.Transaction) -> Unit) {
    val nazwaWydatek = remember { mutableStateOf("") }
    val kwotaWydatek = remember { mutableStateOf("") }

    val czyBladKwoty = kwotaWydatek.value.isNotEmpty() && kwotaWydatek.value.toDoubleOrNull() == null
    val czyPuste = nazwaWydatek.value.isEmpty() || kwotaWydatek.value.isEmpty()
