package com.mrh.ticketsdam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mrh.ticketsdam.ui.theme.TicketsDAMTheme
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                            ),
                            navigationIcon = {
                                if (navBackStackEntry?.destination?.route != "lista_eventos" && navBackStackEntry?.destination?.route != "add_evento") {
                                    IconButton(
                                        onClick = {
                                            navController.popBackStack()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Atrás"
                                        )
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                onClick = {
                                    navController.navigate("lista_eventos")
                                },
                                icon = {
                                    Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                                },
                                label = {
                                    Text("Inicio")
                                },
                                selected = true
                            )
                            NavigationBarItem(
                                onClick = {
                                    navController.navigate("add_evento")
                                },
                                icon = {
                                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                                },
                                label = {
                                    Text("Añadir Evento")
                                },
                                selected = true
                            )
                        }
                    }
                ) { innerPadding ->
                    NavigationHost(modifier = Modifier.padding(innerPadding), navController)
                }
            }
        }
    }
}

@Composable
fun NavigationHost(
    modifier: Modifier,
    navController: NavHostController,
    listaEventosViewModel: ListaEventosViewModel = viewModel()
) {
    NavHost(
        startDestination = "lista_eventos",
        navController = navController,
        modifier = modifier
    ) {
        composable("lista_eventos") {
            ListaEventoScreen(navController = navController, listaEventosViewModel = listaEventosViewModel)
        }
        composable("evento/{id}") { direccion ->
            val id: Int = direccion.arguments!!.getString("id").toString().toInt()
            val listaEventos by listaEventosViewModel.lista.collectAsStateWithLifecycle()
            listaEventos.find { evento -> evento.id == id }?.let { evento ->
                EventoScreen(evento = evento, listaEventosViewModel = listaEventosViewModel)
            }

        }
        composable("add_evento") {
            AddEventoScreen(listaEventosViewModel = listaEventosViewModel)
        }
    }
}

@Composable
fun ListaEventoScreen(

    navController: NavHostController,
    listaEventosViewModel: ListaEventosViewModel = viewModel()
) {
    val listaEventos by listaEventosViewModel.lista.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        var texto by remember { mutableStateOf("") }

        TextField(
            value = texto,
            onValueChange = { textoEscrito ->
                texto = textoEscrito
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        listaEventos.filter { evento -> evento.nombre.uppercase().contains(texto.uppercase()) }
            .forEach { evento ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate("evento/${evento.id}")
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = evento.nombre, fontSize = 24.sp)
                        Text(text = "Fecha: " + evento.fecha.toString())
                    }

                }
            }
    }
}

@Composable
fun EventoScreen(
    evento: Evento,
    listaEventosViewModel: ListaEventosViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(evento.imagen),
            contentDescription = "Imagen",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = evento.nombre,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "Lugar: ${evento.lugar}",
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "Fecha: ${evento.fecha}",
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = "Categoría: ${evento.categoria}",
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            evento.entradas.forEach { entrada ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Tipo: ${entrada.tipo}", fontWeight = FontWeight.Bold)
                            Text("Localidad: ${entrada.localidad}")
                            Text("Precio: ${entrada.precio}€", fontWeight = FontWeight.Bold)
                        }
                        var compradaUsuario by remember { mutableStateOf(entrada.comprada) }
                        Checkbox(
                            checked = compradaUsuario,
                            onCheckedChange = {
                                listaEventosViewModel.marcarEntradaComprada(
                                    idEvento = evento.id,
                                    entrada = entrada
                                )
                                compradaUsuario = !compradaUsuario
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun AddEventoScreen(listaEventosViewModel: ListaEventosViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var texto by remember { mutableStateOf("") }

        TextField(
            value = texto,
            onValueChange = { textoEscrito ->
                texto = textoEscrito
            },
            placeholder = {
                Text("Nombre del evento")
            }
        )
        Button(
            onClick = {
                val format = SimpleDateFormat("yyyy-MM-dd")
                listaEventosViewModel.addEvento(
                    Evento(
                        id = 12,
                        nombre = texto,
                        fecha = format.parse("2027-08-15"),
                        lugar = "Broadway, Nueva York",
                        categoria = "Teatro",
                        entradas = listOf(
                            Entrada(
                                tipo = "Orquesta",
                                precio = 150.0,
                                comprada = false,
                                localidad = "Asientos delanteros"
                            ),
                            Entrada(
                                tipo = "Mezzanine",
                                precio = 100.0,
                                comprada = false,
                                localidad = "Asientos delanteros"
                            )
                        )
                    )
                )
            }
        ) {
            Text("Añadir evento")
        }
    }
}