package com.quickplay.bank_app.ui.adapters.Account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quickplay.bank_app.data.formatNumberCurrency
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.databinding.AccountItemBinding

/**
 * [AccountAdapter] is the adapter for the Recycle view for the [Account].
 * @param listener onClick listener for account item view.
 */
class AccountAdapter(val listener: (account: Account) -> Unit) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private var accounts: MutableList<Account> = mutableListOf()

    /**
     * @param accounts list of Accounts.
     */
    fun setAccountTypeList(accounts: List<Account>) {
        this.accounts.clear()
        this.accounts.addAll(accounts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            AccountItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = accounts.size

    inner class ViewHolder(private val accountItemBinding: AccountItemBinding) :
        RecyclerView.ViewHolder(accountItemBinding.root) {
        init {
            accountItemBinding.root.setOnClickListener {
                listener(accounts[adapterPosition])
            }
        }

        fun bind(account: Account) {
            with(accountItemBinding) {
                this.nameTv.text = account.name
                this.amountValue.text = formatNumberCurrency(account.balance)
                this.accountNumber.text = account.number
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(accounts[position])
    }

}