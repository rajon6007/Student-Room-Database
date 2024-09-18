package com.example.studentroomdatabase

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface StudentDao {
    @Query("SELECT * FROM student_table")
    fun getAll(): List<Student>

    @Query("SELECT * FROM student_table WHERE roll_no = :rollNo")
    suspend fun findByRollNo(rollNo: Int):Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("SELECT(SELECT COUNT(*) FROM student_table) = 0")
    suspend fun isEmpty(): Boolean
}