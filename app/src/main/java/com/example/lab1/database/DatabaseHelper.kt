package com.example.lab1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.lab1.model.Task
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TaskDatabase.db"
        private const val DATABASE_VERSION = 1
        
        // Table name
        private const val TABLE_TASKS = "tasks"
        
        // Column names
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IS_COMPLETED = "is_completed"
        private const val COLUMN_CREATED_AT = "created_at"
        private const val COLUMN_PRIORITY = "priority"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_TASKS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_IS_COMPLETED INTEGER DEFAULT 0,
                $COLUMN_CREATED_AT TEXT NOT NULL,
                $COLUMN_PRIORITY TEXT DEFAULT 'Medium'
            )
        """.trimIndent()
        
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    // Create - Thêm task mới
    fun addTask(task: Task): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_IS_COMPLETED, if (task.isCompleted) 1 else 0)
            put(COLUMN_CREATED_AT, task.createdAt)
            put(COLUMN_PRIORITY, task.priority)
        }
        
        val result = db.insert(TABLE_TASKS, null, values)
        db.close()
        return result
    }

    // Read - Lấy tất cả tasks
    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS ORDER BY $COLUMN_CREATED_AT DESC", null)
        
        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1,
                    createdAt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)),
                    priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
                )
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        
        cursor.close()
        db.close()
        return tasks
    }

    // Update - Cập nhật task
    fun updateTask(task: Task): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_DESCRIPTION, task.description)
            put(COLUMN_IS_COMPLETED, if (task.isCompleted) 1 else 0)
            put(COLUMN_PRIORITY, task.priority)
        }
        
        val result = db.update(TABLE_TASKS, values, "$COLUMN_ID = ?", arrayOf(task.id.toString()))
        db.close()
        return result
    }

    // Delete - Xóa task
    fun deleteTask(taskId: Long): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_TASKS, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
        db.close()
        return result
    }

    // Lấy task theo ID
    fun getTaskById(taskId: Long): Task? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS WHERE $COLUMN_ID = ?", arrayOf(taskId.toString()))
        
        var task: Task? = null
        if (cursor.moveToFirst()) {
            task = Task(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1,
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)),
                priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
            )
        }
        
        cursor.close()
        db.close()
        return task
    }

    // Lấy tasks theo trạng thái
    fun getTasksByStatus(isCompleted: Boolean): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val status = if (isCompleted) 1 else 0
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_TASKS WHERE $COLUMN_IS_COMPLETED = ? ORDER BY $COLUMN_CREATED_AT DESC", 
            arrayOf(status.toString())
        )
        
        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1,
                    createdAt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)),
                    priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
                )
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        
        cursor.close()
        db.close()
        return tasks
    }

    // Utility function để lấy thời gian hiện tại
    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}