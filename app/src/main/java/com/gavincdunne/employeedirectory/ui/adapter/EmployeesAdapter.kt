package com.gavincdunne.employeedirectory.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gavincdunne.employeedirectory.R
import com.gavincdunne.employeedirectory.data.entity.Employee
import com.gavincdunne.employeedirectory.databinding.EmployeesListItemBinding

class EmployeesAdapter : ListAdapter<Employee, EmployeesAdapter.EmployeesViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesViewHolder {
        val binding = EmployeesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class EmployeesViewHolder(private val binding: EmployeesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.apply {
                employeeName.text = employee.fullName
                employeeTeam.text = employee.team

                val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                Glide.with(binding.root)
                    .load(employee.photoSmall)
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_person)
                    .apply(RequestOptions.circleCropTransform())
                    .into(employeeAvatar)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee) = oldItem.uuid == newItem.uuid
        override fun areContentsTheSame(oldItem: Employee, newItem: Employee) = oldItem == newItem
    }
}