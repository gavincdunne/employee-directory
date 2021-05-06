package com.gavincdunne.employeedirectory.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gavincdunne.employeedirectory.R
import com.gavincdunne.employeedirectory.databinding.FragmentEmployeeDetailBinding
import com.gavincdunne.employeedirectory.ui.viewmodel.EmployeeDetailViewModel

class EmployeeDetailFragment : Fragment(R.layout.fragment_employee_detail) {
    private val viewModel: EmployeeDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEmployeeDetailBinding.bind(view)

        binding.apply {
            viewModel.employee?.let {
                val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                Glide.with(binding.root)
                    .load(it.photoLarge)
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_person)
                    .apply(RequestOptions.circleCropTransform())
                    .into(employeeImage)

                employeeEmail.text = it.email
                employeePhone.text = it.phone
                employeeTeam.text = it.team
                employeeType.text = it.type.name
            }


        }
    }
}