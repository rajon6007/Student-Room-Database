package com.example.studentroomdatabase

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentroomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private lateinit var studentDatabase: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        studentDatabase = StudentDatabase.getDatabase(this)

        binding.saveBtn.setOnClickListener {
            saveData()
        }
        binding.searchBtn.setOnClickListener {
            searchData()
        }
        binding.deleteBtn.setOnClickListener {
            GlobalScope.launch {
                studentDatabase.studentDao().deleteAll()
            }
        }

    }


   

    private fun searchData() {
        val rollNo = binding.SearchId.text.toString()
        if (rollNo.isNotEmpty()) {
            lateinit var student: Student
            GlobalScope.launch {
                student = studentDatabase.studentDao().findByRollNo(rollNo.toInt())
                if (studentDatabase.studentDao().isEmpty()) {
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(this@MainActivity, "No Data", Toast.LENGTH_SHORT).show()
                    } 
                    }
                else{
                    displayData(student)
                    
                }
            }
        }
    }

    private fun displayData(student: Student) {

    }

    private fun saveData() {
        val firstName = binding.FirstNameId.text.toString()
        val lastName = binding.LastNameId.text.toString()
        val rollNo = binding.RollNoId.text.toString()
        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()) {
            val student = Student(0, firstName, lastName, rollNo.toInt())
            GlobalScope.launch {
                studentDatabase.studentDao().insert(student)
            }
            binding.FirstNameId.text.clear()
            binding.LastNameId.text.clear()
            binding.RollNoId.text.clear()
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Fill All Fields", Toast.LENGTH_SHORT).show()
        }
    }
}