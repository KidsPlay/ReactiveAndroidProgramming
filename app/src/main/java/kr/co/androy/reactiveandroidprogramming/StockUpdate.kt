package kr.co.androy.reactiveandroidprogramming

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

data class StockUpdate(val stockSymbol: String, val price: BigDecimal, val date: Date) : Serializable