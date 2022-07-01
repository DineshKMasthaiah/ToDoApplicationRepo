package com.example.todoapplication.view.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapplication.R
import com.example.todoapplication.TODOApplication
import com.example.todoapplication.databinding.FragmentTodoListBinding
import com.example.todoapplication.model.ToDo
import com.example.todoapplication.viewmodel.ToDoViewModelFactory
import com.example.todoapplication.viewmodel.todo.ToDoViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a list of Items.
 */
class ToDoListFragment : Fragment() {
    private val todoViewModel: ToDoViewModel by viewModels {
        ToDoViewModelFactory((activity?.application as TODOApplication).repository)
    }
    private lateinit var binding: FragmentTodoListBinding
    private var columnCount = 4
    private var todoList: List<ToDo> = listOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(layoutInflater)

        val view = binding.root
        binding.fab.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_TDTODOListFragment_to_blankFragment)

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = "TODO List"
        binding.list.adapter = ToDoAdapter(todoList)

        todoViewModel.readAllData.observe(viewLifecycleOwner) { todoList ->
            // Update the cached copy of the words in the adapter.
            todoList.let {  binding.list.adapter = ToDoAdapter(it) }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        //todoViewModel.addToDo(ToDo(200,"title","description","16/06/2022",1))
        todoViewModel.readAllData.observe(viewLifecycleOwner) { todoList ->
            // Update the cached copy of the words in the adapter.
            todoList.let {  binding.list.adapter = ToDoAdapter(it) }
        }
        super.onResume()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ToDoListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}