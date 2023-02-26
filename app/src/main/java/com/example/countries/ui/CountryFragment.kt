package com.example.countries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.R
import com.example.countries.adapter.CountryAdapter
import com.example.countries.databinding.FragmentFirstBinding
import com.example.countries.gone
import com.example.countries.utils.NetworkHelper
import com.example.countries.viewmodel.CountryViewModel
import com.example.countries.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CountryFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModels<CountryViewModel>()
    @Inject lateinit var networkHelper: NetworkHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(binding.recyclerView.context, (binding.recyclerView.layoutManager as LinearLayoutManager).orientation))
        }
        if (networkHelper.isNetworkConnected()) {
            apiObserver()
        } else {
            binding.progressBar.hide()
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.check_internet))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.label_ok)){ _, _ ->
                    binding.retry.visible()
                }.show()
        }
        binding.retry.setOnClickListener {
            viewModel.dao.retryFetchData()
            if (networkHelper.isNetworkConnected()) {
                binding.progressBar.show()
                binding.retry.gone()
                apiObserver()
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(getString(R.string.check_internet))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.label_ok)){ _, _ ->
                    }.show()
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun apiObserver() {
        viewModel.dao.loading.observe(viewLifecycleOwner) {
            if(it == true) {
                binding.progressBar.visible()
            }
        }
        viewModel.dao.success.observe(viewLifecycleOwner) {
            it?.let {
                binding.progressBar.gone()
                binding.recyclerView.adapter = it.data?.data?.let { it1 ->
                    CountryAdapter(it1) { it ->
                        val action = CountryFragmentDirections.actionFirstFragmentToSecondFragment(it.toTypedArray())
                        findNavController().navigate(action)
                    }
                }
            }
        }
        viewModel.dao.failure.observe(viewLifecycleOwner) {
            it?.let {
               binding.progressBar.gone()
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(it.first)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.label_ok)){ _, _ ->}.show()
            }
        }
    }
}