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
    }

    override fun onStart() {
        super.onStart()
        Observable
            .just("apple", "google", "twiter")
            .subscribe(object : Consumer<String> {
                override fun accept(stockSymbol: String) {
                    stockDataAdapter?.add(stockSymbol)
                }
            })
    }
}

class StockDataAdapter : RecyclerView.Adapter<StockUpdateViewHolder>() {
    private val data = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockUpdateViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.stock_update_item, parent, false)
        return StockUpdateViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: StockUpdateViewHolder, position: Int) {
        holder.stockSymbol.text = data[position]
    }

    fun add(stockSymbol: String) {
        data.add(stockSymbol)
        notifyItemInserted(data.size - 1)
    }

}

class StockUpdateViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var stockSymbol: TextView = v.findViewById(R.id.stock_item_symbol)

}