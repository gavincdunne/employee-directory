package com.gavincdunne.employeedirectory.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gavincdunne.employeedirectory.R
import com.gavincdunne.employeedirectory.data.entity.Employee
import com.gavincdunne.employeedirectory.databinding.FragmentEmployeesBinding
import com.gavincdunne.employeedirectory.ui.adapter.EmployeesAdapter
import com.gavincdunne.employeedirectory.ui.viewmodel.EmployeesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EmployeesFragment : Fragment(R.layout.fragment_employees),
    EmployeesAdapter.ItemClickListener {

    private val viewModel: EmployeesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEmployeesBinding.bind(view)
        val employeesAdapter = EmployeesAdapter(this)

        binding.apply {
            employeeRecyclerView.apply {
                adapter = employeesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                setHasFixedSize(true)
            }

            employeeListSwipeRefresh.setOnRefreshListener {
                viewModel.readEmployees()
            }
        }

        // Observes employees and updates the recycler adapter with new list
        viewModel.readEmployees().observe(viewLifecycleOwner) {
            employeesAdapter.submitList(it)
        }

        // Observes event state and updates UI
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is EmployeesViewModel.Events.Loading -> {
                        binding.loadingLayout.visibility = View.VISIBLE
                        binding.employeeRecyclerView.visibility = View.GONE
                        binding.errorLayout.visibility = View.GONE
                        binding.emptyResultsLayout.visibility = View.GONE
                    }
                    is EmployeesViewModel.Events.Success -> {
                        binding.loadingLayout.visibility = View.GONE
                        binding.employeeRecyclerView.visibility = View.VISIBLE
                        binding.errorLayout.visibility = View.GONE
                        binding.emptyResultsLayout.visibility = View.GONE
                    }
                    is EmployeesViewModel.Events.Error -> {
                        binding.loadingLayout.visibility = View.GONE
                        binding.employeeRecyclerView.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.emptyResultsLayout.visibility = View.GONE
                        binding.errorTextDetails.text = event.error
                    }
                    is EmployeesViewModel.Events.Empty -> {
                        binding.loadingLayout.visibility = View.GONE
                        binding.employeeRecyclerView.visibility = View.GONE
                        binding.errorLayout.visibility = View.GONE
                        binding.emptyResultsLayout.visibility = View.VISIBLE
                    }
                    is EmployeesViewModel.Events.NavigateToDetails -> {
                        val action = EmployeesFragmentDirections
                            .actionEmployeesFragmentToEmployeeDetailFragment(event.employee, event.employee.fullName)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onClick(employee: Employee) {
        viewModel.navigateToEmployeeDetail(employee)
    }
}