package com.quickplay.bank_app.ui.adapters.Transaction

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quickplay.bank_app.data.formatNumberCurrency
import com.quickplay.bank_app.data.getTime
import com.quickplay.bank_app.data.model.Transaction
import com.quickplay.bank_app.databinding.TransactionItemViewBinding


/**
 * [TransactionAdapter] is the adapter for the Recycle view for the [Transaction].
 */
class TransactionAdapter :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    private var transactions: MutableList<Transaction> = mutableListOf()

    /**
     * @param transactions list of Transactions.
     */
    fun setTransactionTypeList(transactions: List<Transaction>) {
        this.transactions.clear()
        this.transactions.addAll(transactions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            TransactionItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = transactions.size

    inner class ViewHolder(private val transactionItemViewBinding: TransactionItemViewBinding) :
        RecyclerView.ViewHolder(transactionItemViewBinding.root) {

        fun bind(transaction: Transaction) {
            with(transactionItemViewBinding) {
                timeTv.text = getTime(transaction.date)
                if (transaction.additionalTransaction) {
                    this.additionalTransactionTv.visibility = View.VISIBLE
                } else {
                    this.additionalTransactionTv.visibility = View.INVISIBLE
                }
                this.transactionAmount.text = formatNumberCurrency(transaction.amount)
                this.transactionDescription.text = transaction.description
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

}