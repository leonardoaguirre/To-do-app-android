package com.example.todolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddtaskBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import java.util.*

class AddTaskActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityAddtaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddtaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){
            val taskId = intent.getIntExtra(TASK_ID,0)
            TaskDataSource.findById(taskId)?.let {
                binding.tituloInputLayout.text = it.titulo
                binding.dataInputLayout.text = it.data
                binding.descricaoInputLayout.text = it.descricao
                binding.horaInputLayout.text = it.hora
                binding.btnCriarTarefa.text = ATUALIZAR_TAREFA
            }
        }

        insertListeners()
    }

    private fun insertListeners(){
        binding.dataInputLayout.editText?.setOnClickListener(){
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) *-1
                binding.dataInputLayout.text= Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager,"DATE_TAG")
        }

        binding.horaInputLayout.editText?.setOnClickListener(){
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener(){
                val H = if(timePicker.hour in 0..9) "0${timePicker.hour}" else "${timePicker.hour}"
                val m = if(timePicker.minute in 0..9) "0${timePicker.minute}" else "${timePicker.minute}"
                binding.horaInputLayout.text = "${H}:${m}"
            }
            timePicker.show(supportFragmentManager,"TIME_TAG")
        }

        binding.btnCancelar.setOnClickListener(){
            finish()
        }
        binding.btnCriarTarefa.setOnClickListener(){
            val task = Task(
                titulo = binding.tituloInputLayout.text,
                descricao = binding.descricaoInputLayout.text,
                data = binding.dataInputLayout.text,
                hora = binding.horaInputLayout.text,
                id = intent.getIntExtra(TASK_ID,0)
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    companion object{
        const val  TASK_ID = "task_id"
        const val ATUALIZAR_TAREFA ="Atualizar Tarefa"
    }
}