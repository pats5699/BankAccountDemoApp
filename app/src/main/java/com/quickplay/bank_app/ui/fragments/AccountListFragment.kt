package com.quickplay.bank_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.quickplay.bank_app.Constant
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.formatNumberCurrency
import com.quickplay.bank_app.data.showErrorSnackBar
import com.quickplay.bank_app.databinding.FragmentAccountListBinding
import com.quickplay.bank_app.ui.adapters.Account.AccountTypeAdapter
import com.quickplay.bank_app.ui.viewmodels.AccountListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountListFragment : Fragment() {

    private var _binding: FragmentAccountListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountListViewModel by viewModels()

    private lateinit var accountTypeAdapter: AccountTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(binding.toolbar, navHostFragment)

        initRecycleView()
        updateUIOnStatusChange()
        with(viewModel) {
            accountsByType.observe(viewLifecycleOwner) {
                accountTypeAdapter.setAccountTypeList(it)
            }
            iOweAmount.observe(viewLifecycleOwner) {
                binding.totalAmountCv.iOweAmountTv.text = formatNumberCurrency(it.toString())
            }
            iHaveAmount.observe(viewLifecycleOwner) {
                binding.totalAmountCv.iHaveAmountTv.text = formatNumberCurrency(it.toString())
            }
        }

        viewModel.fetchFromNetwork()
    }

    private fun updateUIOnStatusChange() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
        viewModel.loadingStatus.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            when (it) {
                is Resource.Success -> {
                    binding.loadingStatus.visibility = View.INVISIBLE

                }
                is Resource.Loading -> {
                    binding.loadingStatus.visibility = View.VISIBLE

                }
                else -> {
                    binding.loadingStatus.visibility = View.INVISIBLE
                    showErrorSnackBar(
                        Constant.ACCOUNT_ERROR,
                        requireActivity()
                    )
                }
            }

        }
    }

    private fun initRecycleView() {
        accountTypeAdapter = AccountTypeAdapter(requireContext()) {
            val action =
                AccountListFragmentDirections.actionAccountListFragmentToDetailAccountFragment(it)
            findNavController().navigate(action)
        }
        binding.accountTypeRecycleView.layoutManager = LinearLayoutManager(context)
        binding.accountTypeRecycleView.adapter = accountTypeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}