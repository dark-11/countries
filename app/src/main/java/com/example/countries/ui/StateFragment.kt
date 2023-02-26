package com.example.countries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.adapter.StateAdapter
import com.example.countries.databinding.FragmentSecondBinding
import com.example.countries.gone
import com.example.countries.viewmodel.StateViewModel
import com.example.countries.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StateFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val args: StateFragmentArgs by navArgs()
    private val states by lazy {
        args.states
    }
    private val viewModel by viewModels<StateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        viewModel.responseOfStates.value = states.isNotEmpty()

        if (states.isNullOrEmpty()) {
            binding.emptyText.visible()
            binding.recyclerView.gone()
            binding.progressBar.hide()
        } else {
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                addItemDecoration(DividerItemDecoration(binding.recyclerView.context, (binding.recyclerView.layoutManager as LinearLayoutManager).orientation))
                adapter = StateAdapter(states)
            }
        }
        apiObserver()
        return binding.root

    }
    private fun apiObserver() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visible()
        }

        viewModel.success.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
        }

        viewModel.failure.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
        }
    }
}