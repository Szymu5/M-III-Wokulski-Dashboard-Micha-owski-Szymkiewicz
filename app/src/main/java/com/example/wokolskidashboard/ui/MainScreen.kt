package com.example.wokulskidashboard.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.wokulskidashboard.model.Transaction
import com.example.wokulskidashboard.ui.components.IncomeForm

@Composable
fun MainScreen() {

    val listaTransakcji = remember { mutableStateListOf<Transaction>() }

    Column {

        var saldo = 0.0
        listaTransakcji.forEach { t ->
            if (t.isExpense) {
                saldo = saldo - t.amount
            } else {
                saldo = saldo + t.amount
            }
        }

        Text(text = "Saldo sklepu: $saldo rubli")


        IncomeForm(dodajPrzychod = { nowa ->
            listaTransakcji.add(nowa)
        })


        LazyColumn {
            items(listaTransakcji) { element ->
                val znak = if (element.isExpense) "-" else "+"
                Text("${element.title}: $znak${element.amount} rub.")
            }
        }
    }
}
