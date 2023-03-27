package com.quickplay.bank_app.ui.adapters.Account

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.databinding.AccountListGroupBinding

/**
 * [AccountTypeAdapter] is the adapter for the Recycle view for the [AccountType].
 * It is nested recycle view. Parent Recycle view is of Account type and
 * child recycle view contains accounts Of specific AccountType.
 * @param context Application Context
 * @param listener child item view onClickListener.
 */
class AccountTypeAdapter(
    private val context: Context,
    private val listener: (account: Account) -> Unit
) :
    RecyclerView.Adapter<AccountTypeAdapter.ViewHolder>() {

    private val accountType: MutableList<String> = mutableListOf()
    private val accountsWithType: HashMap<String, List<Account>> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AccountListGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = accountType.size

    /**
     * @param accounts here the key is account type and list contains accounts of that type.
     */
    fun setAccountTypeList(accounts: Map<String, List<Account>>) {
        accountType.clear()
        this.accountsWithType.clear()
        accountType.addAll(accounts.keys)
        this.accountsWithType.putAll(accounts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val accountListGroupBinding: AccountListGroupBinding) :
        RecyclerView.ViewHolder(accountListGroupBinding.root) {

        fun bind(position: Int) {
            val accountTypeString = accountType[position]
            val accounts: List<Account>? = accountsWithType[accountTypeString]
            val accountAdapter = AccountAdapter(listener = listener)

            with(accountListGroupBinding) {
                dateValue.text = accountTypeString
                transactionNestedRecycleview.layoutManager = LinearLayoutManager(context)
                transactionNestedRecycleview.adapter = accountAdapter
            }
            accounts?.let {
                accountAdapter.setAccountTypeList(it)
            }
        }
    }
}