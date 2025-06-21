package com.example.lab1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.model.Trainee

class TraineeAdapter(
    private var trainees: MutableList<Trainee> = mutableListOf(),
    private val onEditClick: (Trainee) -> Unit,
    private val onDeleteClick: (Trainee) -> Unit
) : RecyclerView.Adapter<TraineeAdapter.TraineeViewHolder>() {
    
    class TraineeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTraineeName: TextView = itemView.findViewById(R.id.tv_trainee_name)
        val tvTraineeEmail: TextView = itemView.findViewById(R.id.tv_trainee_email)
        val tvTraineePhone: TextView = itemView.findViewById(R.id.tv_trainee_phone)
        val tvTraineeGender: TextView = itemView.findViewById(R.id.tv_trainee_gender)
        val tvTraineeId: TextView = itemView.findViewById(R.id.tv_trainee_id)
        val btnEditTrainee: Button = itemView.findViewById(R.id.btn_edit_trainee)
        val btnDeleteTrainee: Button = itemView.findViewById(R.id.btn_delete_trainee)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraineeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trainee, parent, false)
        return TraineeViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: TraineeViewHolder, position: Int) {
        val trainee = trainees[position]
        
        holder.tvTraineeName.text = "Tên: ${trainee.name}"
        holder.tvTraineeEmail.text = "Email: ${trainee.email}"
        holder.tvTraineePhone.text = "SĐT: ${trainee.phone}"
        holder.tvTraineeGender.text = "Giới tính: ${trainee.gender}"
        holder.tvTraineeId.text = "ID: ${trainee.id}"
        
        holder.btnEditTrainee.setOnClickListener {
            onEditClick(trainee)
        }
        
        holder.btnDeleteTrainee.setOnClickListener {
            onDeleteClick(trainee)
        }
    }
    
    override fun getItemCount(): Int = trainees.size
    
    fun updateTrainees(newTrainees: List<Trainee>) {
        trainees.clear()
        trainees.addAll(newTrainees)
        notifyDataSetChanged()
    }
    
    fun addTrainee(trainee: Trainee) {
        trainees.add(trainee)
        notifyItemInserted(trainees.size - 1)
    }
    
    fun removeTrainee(trainee: Trainee) {
        val position = trainees.indexOf(trainee)
        if (position != -1) {
            trainees.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    
    fun updateTrainee(updatedTrainee: Trainee) {
        val position = trainees.indexOfFirst { it.id == updatedTrainee.id }
        if (position != -1) {
            trainees[position] = updatedTrainee
            notifyItemChanged(position)
        }
    }
}