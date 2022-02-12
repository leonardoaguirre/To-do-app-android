package com.example.todolist.datasource

import com.example.todolist.model.Task

object TaskDataSource{
    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task : Task){
        if(task.id == 0) list.add(task.copy(list.size+1))
        else {
            list.remove(task)
            list.add (task)
        }
    }

    fun findById(taskid : Int) =  list.find { it.id == taskid }

    fun deleteTask(task: Task) {
        list.remove(task)
    }
}