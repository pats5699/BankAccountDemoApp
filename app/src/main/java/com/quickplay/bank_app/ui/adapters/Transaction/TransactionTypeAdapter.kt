package com.quickplay.bank_app.ui.adapters.Transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickplay.bank_app.R

import com.quickplay.bank_app.data.model.Transaction
import com.quickplay.bank_app.databinding.TransactionListGroupViewBinding


class TransactionTypeAdapter(private val context: Context) :
    RecyclerView.Adapter<TransactionTypeAdapter.ViewHolder>() {

    private val transactionType: MutableList<String> = mutableListOf()
    private val transactionsWithType: HashMap<String, List<Transaction>> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TransactionListGroupViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = transactionType.size

    fun setTransactionTypeList(transactions: Map<String, List<Transaction>>) {
        transactionType.clear()
        this.transactionsWithType.clear()
        transactionType.addAll(transactions.keys)
        this.transactionsWithType.putAll(transactions)
        notifyDataSetChanged()
    }

     inner class ViewHolder(private val transactionListGroupBinding: TransactionListGroupViewBinding) :
        RecyclerView.ViewHolder(transactionListGroupBinding.root) {

        fun bind(position: Int) {
            val transactionTypeString = transactionType[position]
            val transactions: List<Transaction>? = transactionsWithType[transactionTypeString]
            val transactionAdapter = TransactionAdapter()

            with(transactionListGroupBinding) {
                dateValue.text = context.resources.getString(R.string.date_value, transactionTypeString)
                transactionNestedRecycleview.layoutManager = LinearLayoutManager(context)
                transactionNestedRecycleview.adapter = transactionAdapter
            }
            transactions?.let {
                transactionAdapter.setTransactionTypeList(it)
            }
        }
    }
}