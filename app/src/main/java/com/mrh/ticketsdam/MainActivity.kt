package com.mrh.ticketsdam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrh.ticketsdam.ui.theme.TicketsDAMTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicketsDAMTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Tickets DAM")
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                ) { innerPadding ->
                    ListaEventoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListaEventoScreen(modifier: Modifier,listaEventosViewModel: ListaEventosViewModel = viewModel()){
    val listaEventos by listaEventosViewModel.lista.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState())){

        var texto by remember { mutableStateOf("") }

        TextField(
            value = texto,
            onValueChange = { textoEscrito ->
                texto = textoEscrito
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        listaEventos.forEach { evento ->
            Card (
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ){
                Column (
                    modifier = Modifier.padding(16.dp)
                ){
                    Text(text = evento.nombre, fontSize = 24.sp)
                    Text(text = "Fecha: "+ evento.fecha.toString())
                }

            }
        }
    }
}
