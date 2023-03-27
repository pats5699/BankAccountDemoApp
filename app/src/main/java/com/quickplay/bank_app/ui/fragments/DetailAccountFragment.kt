package com.quickplay.bank_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.quickplay.bank_app.Constant
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.formatNumberCurrency
import com.quickplay.bank_app.data.repository.transaction.TransactionRepository.Companion.ADDITIONAL_TRANSLATION
import com.quickplay.bank_app.data.repository.transaction.TransactionRepository.Companion.ALL_TRANSLATION
import com.quickplay.bank_app.data.showErrorSnackBar
import com.quickplay.bank_app.databinding.FragmentDetailAccountBinding
import com.quickplay.bank_app.di.ViewModelFactories
import com.quickplay.bank_app.ui.adapters.Transaction.TransactionTypeAdapter
import com.quickplay.bank_app.ui.viewmodels.AccountDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailAccountFragment : Fragment() {

    private var _binding: FragmentDetailAccountBinding? = null
    private val binding get() = _binding!!

    private val navArgs: DetailAccountFragmentArgs by navArgs()

    @Inject
    lateinit var assistedFactory: ViewModelFactories.AccountDetailViewModelAssistedFactory
    private val viewModel: AccountDetailViewModel by viewModels {
        AccountDetailViewModel.Factory(assistedFactory, navArgs.account)
    }

    private lateinit var transactionTypeAdapter: TransactionTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        navigateBackSetup()
        updateAccountUI()
        updateUI()
        viewModel.transactionByTime.observe(viewLifecycleOwner) {
            transactionTypeAdapter.setTransactionTypeList(it)
        }

        viewModel.fetchFromNetwork()
    }

    private fun navigateBackSetup() {
        binding.accountInformation.navigateBack.setOnClickListener {
            val action =
                DetailAccountFragmentDirections.actionDetailAccountFragmentToAccountListFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateAccountUI() {
        with(binding.accountInformation) {
            viewModel.account.let {
                amountValueTv.text = formatNumberCurrency(it.balance)
                accountNumberValueTv.text = it.number
                accountNameValueTv.text = it.name
            }
        }

    }

    private fun initRecycleView() {
        transactionTypeAdapter = TransactionTypeAdapter(requireContext())
        binding.transactionTypeRecycleview.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = transactionTypeAdapter
        }
    }

    private fun updateUI() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            val allTransactionResource: Resource = it[ALL_TRANSLATION]

            val additionalTransactionResource = if (it.size == 2) {
                it[ADDITIONAL_TRANSLATION]
            } else {
                null
            }

            if (allTransactionResource is Resource.Loading) {
                binding.loadingStatus.visibility = View.VISIBLE
            } else {
                binding.loadingStatus.visibility = View.INVISIBLE
                if (allTransactionResource is Resource.Error && additionalTransactionResource is Resource.Error) {
                    showErrorSnackBar(
                        Constant.BOTH_TRANSACTION_ERROR,
                        requireActivity()
                    )
                } else if (allTransactionResource is Resource.Error) {
                    showErrorSnackBar(
                        Constant.ALL_TRANSACTION_ERROR,
                        requireActivity()
                    )
                } else if (additionalTransactionResource is Resource.Error) {
                    showErrorSnackBar(
                        Constant.ADDITIONAL_TRANSACTION_ERROR,
                        requireActivity()
                    )
                }
            }

        }

    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }
}
