package com.example.todoapplication.view.home

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.example.todoapplication.R
import com.example.todoapplication.TODOApplication
import com.example.todoapplication.databinding.FragmentAddTodoBinding
import com.example.todoapplication.model.ToDo
import com.example.todoapplication.viewmodel.ToDoViewModelFactory
import com.example.todoapplication.viewmodel.todo.ToDoViewModel
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var day = 0
var month: Int = 0
var year: Int = 0
var hour: Int = 0
var minute: Int = 0
var myDay = 0
var myMonth: Int = 0
var myYear: Int = 0
var myHour: Int = 0
var myMinute: Int = 0

/**
 * A simple [Fragment] subclass.
 * Use the [AddToDoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddToDoFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAddTodoBinding
    private val todoViewModel: ToDoViewModel by viewModels {
        ToDoViewModelFactory((activity?.application as TODOApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dateTv.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(activity?.baseContext!!, this, year, month, day)
            datePickerDialog.show()
        }
        activity?.title = "Add or Edit TODO"
        val id = arguments?.getInt("id")
        if (id != null) {
            todoViewModel.fetchToDoById(id.toString()).asLiveData().observe(viewLifecycleOwner) {
                binding.titleTv1.setText(it?.title)
                binding.descTv.setText(it?.description)
                binding.dateTv.setText(it?.dateAndTime)
            }
        }
        binding.addBtn.setOnClickListener {

            todoViewModel.addToDo(
                ToDo(
                    10,
                    binding.titleTv1.text.toString(),
                    binding.descTv.text.toString(),
                    binding.dateTv.text.toString(),
                    1
                )
            )
            val bundle = bundleOf(
                "id" to id,
                "title" to binding.titleTv1.text.toString(),
                "description" to binding.descTv.text.toString(),
                "date" to binding.dateTv.text.toString()
            )
            Navigation.findNavController(it).setGraph(R.navigation.my_nav, bundle)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        binding.addBtn.setOnClickListener {
            todoViewModel.addToDo(
                ToDo(
                    1,
                    binding.titleTv1.text.toString(),
                    binding.descTv.text.toString(),
                    binding.dateTv.text.toString(),
                    1
                )
            )
        }
        super.onResume()
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            activity, this, hour, minute,
            DateFormat.is24HourFormat(activity)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        // textView.text = "Year: " + myYear + "\n" + "Month: " + myMonth + "\n" + "Day: " + myDay + "\n" + "Hour: " + myHour + "\n" + "Minute: " + myMinute
    }
}