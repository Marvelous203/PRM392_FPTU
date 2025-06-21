package com.example.lab1.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.model.Task

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onTaskClick: (Task) -> Unit,
    private val onTaskStatusChange: (Task) -> Unit,
    private val onTaskEdit: (Task) -> Unit,
    private val onTaskDelete: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_task_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_task_description)
        val tvCreatedAt: TextView = itemView.findViewById(R.id.tv_task_created_at)
        val tvPriority: TextView = itemView.findViewById(R.id.tv_task_priority)
        val cbCompleted: CheckBox = itemView.findViewById(R.id.cb_task_completed)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit_task)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        
        holder.tvTitle.text = task.title
        holder.tvDescription.text = task.description
        holder.tvCreatedAt.text = task.createdAt
        holder.tvPriority.text = task.priority
        holder.cbCompleted.isChecked = task.isCompleted
        
        // Thiết lập màu sắc theo độ ưu tiên
        val priorityColor = when (task.priority) {
            "High" -> ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light)
            "Medium" -> ContextCompat.getColor(holder.itemView.context, android.R.color.holo_orange_light)
            "Low" -> ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_light)
            else -> ContextCompat.getColor(holder.itemView.context, android.R.color.darker_gray)
        }
        holder.tvPriority.setTextColor(priorityColor)
        
        // Thiết lập giao diện cho task đã hoàn thành
        if (task.isCompleted) {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvDescription.paintFlags = holder.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.alpha = 0.6f
        } else {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tvDescription.paintFlags = holder.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.itemView.alpha = 1.0f
        }
        
        // Click listeners
        holder.itemView.setOnClickListener {
            onTaskClick(task)
        }
        
        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            val updatedTask = task.copy(isCompleted = isChecked)
            onTaskStatusChange(updatedTask)
        }
        
        holder.btnEdit.setOnClickListener {
            onTaskEdit(task)
        }
        
        holder.btnDelete.setOnClickListener {
            onTaskDelete(task)
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    fun addTask(task: Task) {
        tasks.add(0, task) // Thêm vào đầu danh sách
        notifyItemInserted(0)
    }

    fun removeTask(task: Task) {
        val position = tasks.indexOfFirst { it.id == task.id }
        if (position != -1) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateTask(updatedTask: Task) {
        val position = tasks.indexOfFirst { it.id == updatedTask.id }
        if (position != -1) {
            tasks[position] = updatedTask
            notifyItemChanged(position)
        }
    }
}