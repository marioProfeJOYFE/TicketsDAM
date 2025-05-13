package com.mrh.ticketsdam

import android.icu.text.DateFormat
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ListaEventosViewModel : ViewModel() {

    //_lista es privado y mutable, solo el ViewModel puede modificarlo
    private val _lista = MutableStateFlow<List<Evento>>(emptyList())

    // lista es público e inmutable (StateFlow), para ser observada por la Interfaz de Usuario.
    val lista: StateFlow<List<Evento>> = _lista.asStateFlow()


    // Funcion para añadir un item a la lista
    fun addEvento(evento: Evento) {
        _lista.update { listaActual  ->
            listaActual + evento
        }
    }

    // Funcion para marcar una entrada como comprada
    fun marcarEntradaComprada(idEvento: Int, entrada: Entrada) {
        _lista.update { listaActual  ->
            // Recorremos la lista de eventos
            listaActual.map { evento ->
                // Si el id del evento coincide, modificamos la lista de entradas
                if (evento.id == idEvento) {
                    // Recorremos la lista de entradas
                    evento.copy(entradas = evento.entradas.map {entradaActual ->
                        // Si la entrada coincide, la marcamos como comprada
                        if (entradaActual == entrada) {
                            entradaActual.copy(comprada = true)
                        }
                        // Si no, la dejamos igual
                        else {
                            entradaActual
                        }
                    })
                }
                // Si no, dejamos el evento igual
                else {
                    evento
                }
            }
        }
    }


    init {
        cargarListaInicial()
    }

    private fun cargarListaInicial(){
        val format = SimpleDateFormat("yyyy-MM-dd")
        viewModelScope.launch {
            _lista.value = listOf(
                Evento(
                    id = 1,
                    nombre = "Debi Tirar Mas Fotos World Tour",
                    fecha = format.parse("2026-05-30"),
                    lugar = "Madrid",
                    categoria = "Concierto",
                    entradas = listOf(
                        Entrada(
                            tipo = "Entrada General",
                            precio = 120.0,
                            comprada = false,
                            localidad = "Pista"
                        ),
                        Entrada(
                            tipo = "Entrada VIP",
                            precio = 250.0,
                            comprada = false,
                            localidad = "Zona Exclusiva"
                        )
                    )
                ),
                Evento(
                    id = 2,
                    nombre = "Festival de Cine Independiente",
                    fecha = format.parse("2026-06-15"),
                    lugar = "Barcelona",
                    categoria = "Festival",
                    entradas = listOf(
                        Entrada(
                            tipo = "Pase de Día",
                            precio = 30.0,
                            comprada = false,
                            localidad = "Acceso a todas las proyecciones del día"
                        ),
                        Entrada(
                            tipo = "Pase Completo",
                            precio = 80.0,
                            comprada = false,
                            localidad = "Acceso a todas las proyecciones y eventos"
                        )
                    )
                ),
                Evento(
                    id = 3,
                    nombre = "Partido de Baloncesto: Lakers vs Celtics",
                    fecha = format.parse("2026-07-10"),
                    lugar = "Los Ángeles",
                    categoria = "Deporte",
                    entradas = listOf(
                        Entrada(
                            tipo = "Grada Superior",
                            precio = 50.0,
                            comprada = false,
                            localidad = "Sección 305"
                        ),
                        Entrada(
                            tipo = "Grada Inferior",
                            precio = 150.0,
                            comprada = false,
                            localidad = "Sección 102"
                        ),
                        Entrada(
                            tipo = "Asiento a Pie de Pista",
                            precio = 500.0,
                            comprada = false,
                            localidad = "Primera Fila"
                        )
                    )
                ),
                Evento(
                    id = 4,
                    nombre = "Exposición de Arte Moderno",
                    fecha = format.parse("2026-08-01"),
                    lugar = "París",
                    categoria = "Exposición",
                    entradas = listOf(
                        Entrada(
                            tipo = "Entrada General",
                            precio = 20.0,
                            comprada = false,
                            localidad = "Acceso a todas las salas"
                        ),
                        Entrada(
                            tipo = "Entrada con Guía",
                            precio = 35.0,
                            comprada = false,
                            localidad = "Tour guiado por la exposición"
                        )
                    )
                ),
                Evento(
                    id = 5,
                    nombre = "Obra de Teatro: Hamlet",
                    fecha = format.parse("2026-09-05"),
                    lugar = "Londres",
                    categoria = "Teatro",
                    entradas = listOf(
                        Entrada(
                            tipo = "Patio de Butacas",
                            precio = 80.0,
                            comprada = false,
                            localidad = "Fila 10, Asiento 5"
                        ),
                        Entrada(
                            tipo = "Anfiteatro",
                            precio = 40.0,
                            comprada = false,
                            localidad = "Fila 5, Asiento 20"
                        )
                    )
                ),
                Evento(
                    id = 6,
                    nombre = "Conferencia de Tecnología: El Futuro de la IA",
                    fecha = format.parse("2026-10-20"),
                    lugar = "San Francisco",
                    categoria = "Conferencia",
                    entradas = listOf(
                        Entrada(
                            tipo = "Pase Estándar",
                            precio = 300.0,
                            comprada = false,
                            localidad = "Acceso a todas las charlas"
                        ),
                        Entrada(
                            tipo = "Pase Premium",
                            precio = 600.0,
                            comprada = false,
                            localidad = "Acceso a charlas, talleres y networking"
                        )
                    )
                ),
                Evento(
                    id = 7,
                    nombre = "Concierto de Música Clásica: Beethoven Sinfonía No. 9",
                    fecha = format.parse("2026-11-11"),
                    lugar = "Viena",
                    categoria = "Concierto",
                    entradas = listOf(
                        Entrada(
                            tipo = "Asiento de Orquesta",
                            precio = 100.0,
                            comprada = false,
                            localidad = "Fila 5"
                        ),
                        Entrada(
                            tipo = "Balcón",
                            precio = 60.0,
                            comprada = false,
                            localidad = "Sección A"
                        )
                    )
                ),
                Evento(
                    id = 8,
                    nombre = "Carrera de Fórmula 1: Gran Premio de Mónaco",
                    fecha = format.parse("2027-05-28"),
                    lugar = "Mónaco",
                    categoria = "Deporte",
                    entradas = listOf(
                        Entrada(
                            tipo = "Grada K",
                            precio = 800.0,
                            comprada = false,
                            localidad = "Vista de la curva de la piscina"
                        ),
                        Entrada(
                            tipo = "Paddock Club",
                            precio = 5000.0,
                            comprada = false,
                            localidad = "Acceso VIP al paddock"
                        )
                    )
                ),
                Evento(
                    id = 9,
                    nombre = "Festival de Jazz de Nueva Orleans",
                    fecha = format.parse("2027-04-28"),
                    lugar = "Nueva Orleans",
                    categoria = "Festival",
                    entradas = listOf(
                        Entrada(
                            tipo = "Pase de Fin de Semana",
                            precio = 200.0,
                            comprada = false,
                            localidad = "Acceso a todos los escenarios"
                        ),
                        Entrada(
                            tipo = "Pase VIP",
                            precio = 500.0,
                            comprada = false,
                            localidad = "Acceso a zonas exclusivas y eventos especiales"
                        )
                    )
                ),
                Evento(
                    id = 10,
                    nombre = "Exposición de Dinosaurios",
                    fecha = format.parse("2027-07-01"),
                    lugar = "Nueva York",
                    categoria = "Exposición",
                    entradas = listOf(
                        Entrada(
                            tipo = "Entrada General",
                            precio = 25.0,
                            comprada = false,
                            localidad = "Acceso a la exposición principal"
                        ),
                        Entrada(
                            tipo = "Entrada Familiar",
                            precio = 80.0,
                            comprada = false,
                            localidad = "Para 4 personas"
                        )
                    )
                ),
                Evento(
                    id = 11,
                    nombre = "Musical: El Rey León",
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
    }







}