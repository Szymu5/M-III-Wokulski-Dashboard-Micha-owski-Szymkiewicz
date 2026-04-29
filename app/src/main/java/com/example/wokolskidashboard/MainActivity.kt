package com.example.wokolskidashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wokolskidashboard.model.Transaction
import com.example.wokolskidashboard.ui.theme.WokolskiDashBoardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WokolskiDashBoardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val listaTransakcji = remember { mutableStateListOf<Transaction>() }
    val saldo = listaTransakcji.sumOf { if (it.isExpense) -it.amount else it.amount }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "SALDO: $saldo rubli", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        ExpenseForm(dodajWydatek = { listaTransakcji.add(it) })

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Historia transakcji:", fontWeight = FontWeight.Bold)

        LazyColumn {
            items(listaTransakcji) { t ->
                Text(
                    text = "${t.title}: ${t.amount} rub.",
                    color = if (t.isExpense) Color.Red else Color.Green
                )
            }
        }
    }
}

@Composable
fun ExpenseForm(dodajWydatek: (Transaction) -> Unit) {
    val nazwaWydatek = remember { mutableStateOf("") }
    val kwotaWydatek = remember { mutableStateOf("") }

    val czyBladKwoty = kwotaWydatek.value.isNotEmpty() && kwotaWydatek.value.toDoubleOrNull() == null
    val czyPuste = nazwaWydatek.value.isEmpty() || kwotaWydatek.value.isEmpty()

    Column {
        Text(text = "Dodaj wydatek:", color = Color.Red, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = nazwaWydatek.value,
            onValueChange = { nazwaWydatek.value = it },
            label = { Text("Co kupiono?") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = kwotaWydatek.value,
            onValueChange = { kwotaWydatek.value = it },
            label = { Text("Koszt") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            enabled = !czyBladKwoty && !czyPuste,
            onClick = {
                val kasa = kwotaWydatek.value.toDoubleOrNull() ?: 0.0
                dodajWydatek(Transaction(nazwaWydatek.value, kasa, true))
                nazwaWydatek.value = ""
                kwotaWydatek.value = ""
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Zapisz wydatek")
        }
    }
}