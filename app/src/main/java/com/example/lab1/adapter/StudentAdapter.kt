package com.example.lab1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.lab1.R
import com.example.lab1.model.Student

class StudentAdapter(private val context: Context, private val students: List<Student>) : BaseAdapter() {
    
    override fun getCount(): Int = students.size
    
    override fun getItem(position: Int): Any = students[position]
    
    override fun getItemId(position: Int): Long = students[position].id.toLong()
    
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
            holder = ViewHolder(
                view.findViewById(R.id.iv_avatar),
                view.findViewById(R.id.tv_name),
                view.findViewById(R.id.tv_email),
                view.findViewById(R.id.tv_phone)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        
        val student = students[position]
        
        // Sử dụng hình ảnh khác nhau dựa trên ID
        when (student.id) {
            1 -> holder.ivAvatar.setImageResource(R.drawable.person_1)
            2 -> holder.ivAvatar.setImageResource(R.drawable.person_2)
            3 -> holder.ivAvatar.setImageResource(R.drawable.person_3)
            4 -> holder.ivAvatar.setImageResource(R.drawable.person_4)
            5 -> holder.ivAvatar.setImageResource(R.drawable.person_5)
            6 -> holder.ivAvatar.setImageResource(R.drawable.person_6)
            7 -> holder.ivAvatar.setImageResource(R.drawable.person_7)
            8 -> holder.ivAvatar.setImageResource(R.drawable.person_8)
            else -> holder.ivAvatar.setImageResource(R.drawable.ic_person)
        }
        
        holder.tvName.text = student.name
        holder.tvEmail.text = student.email
        holder.tvPhone.text = student.phone
        
        return view
    }
    
    private data class ViewHolder(
        val ivAvatar: ImageView,
        val tvName: TextView,
        val tvEmail: TextView,
        val tvPhone: TextView
    )
}