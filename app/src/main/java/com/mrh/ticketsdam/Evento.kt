package com.mrh.ticketsdam

import java.util.Date

data class Evento(
    val id : Int,
    val nombre : String,
    val entradas: List<Entrada>,
    val lugar : String,
    val fecha: Date,
    val categoria: String
)
