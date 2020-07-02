package com.example.datastorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    fun insertRecord(view: View){
        val insertReturn = viewModel.insert(this, u_id, u_name, u_email)
        if(insertReturn > -1){
            Toast.makeText(this,"record insert", Toast.LENGTH_LONG).show()
            u_id.text.clear()
            u_name.text.clear()
            u_email.text.clear()
        } else {
            Toast.makeText(this,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }
    }

    fun viewRecord(view: View){
        viewModel.view(this, listView)
    }

    fun updateRecord(view: View){
        viewModel.update(this)
    }

    fun deleteRecord(view: View){
        viewModel.delete(this)
    }
}