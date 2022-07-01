package com.example.todoapplication.view.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.todoapplication.R

import com.example.todoapplication.databinding.LayoutTodoItemTemplateBinding
import com.example.todoapplication.model.ToDo


class ToDoAdapter(
    private val values: List<ToDo>
) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutTodoItemTemplateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idTv.text = item.id.toString()
        holder.titleTv.text = item.title
        holder.descTv.text = item.description
        holder.dateTv.text = item.dateAndTime
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("id" to item.id)
            Navigation.findNavController(it)
                .navigate(R.id.action_TDTODOListFragment_to_blankFragment, bundle)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: LayoutTodoItemTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val titleTv: TextView = binding.titleTv
        val idTv: TextView = binding.idTv
        val descTv: TextView = binding.descriptionTv
        val dateTv: TextView = binding.dtTv

        override fun toString(): String {
            return super.toString() + " '" + titleTv.text + "'"
        }
    }

}