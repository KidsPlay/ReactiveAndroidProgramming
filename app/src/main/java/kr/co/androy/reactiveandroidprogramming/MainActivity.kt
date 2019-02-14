package kr.co.androy.reactiveandroidprogramming

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var layoutManager: LinearLayoutManager? = null
    private var stockDataAdapter: StockDataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        stockDataAdapter = StockDataAdapter()
        recyclerView.adapter = stockDataAdapter

        Observable.just(
                StockUpdate("google", BigDecimal(12.43), Date()),
                StockUpdate("apple", BigDecimal(645.1), Date()),
                StockUpdate("twiter", BigDecimal(1.43), Date())
        ).subscribe(object : Consumer<StockUpdate> {
            override fun accept(stock: StockUpdate) {
                stockDataAdapter?.add(stock)
            }

        })
    }

    override fun onStart() {
        super.onStart()

    }
}

class StockDataAdapter : RecyclerView.Adapter<StockUpdateViewHolder>() {
    private val data = mutableListOf<StockUpdate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockUpdateViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.stock_update_item, parent, false)
        return StockUpdateViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StockUpdateViewHolder, position: Int) {
        data[position].apply {
            holder.setStockSymbol(stockSymbol)
            holder.setPrice(price)
            holder.setDate(date)
        }
    }

    fun add(stockSymbol: StockUpdate) {
        data.add(stockSymbol)
        notifyItemInserted(data.size - 1)
    }

}

class StockUpdateViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private var stockSymbol: TextView = v.findViewById(R.id.stock_item_symbol)
    private var stockPrice: TextView = v.findViewById(R.id.stock_item_price)
    private var stockDate: TextView = v.findViewById(R.id.stock_item_date)

    private val priceFormat = DecimalFormat("#0.00")

    fun setStockSymbol(symbol: String) {
        stockSymbol.text = symbol
    }

    fun setPrice(price: BigDecimal) {
        stockPrice.text = priceFormat.format(price.toFloat())
    }

    fun setDate(date: Date) {
        stockDate.text = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm", date)
    }
}