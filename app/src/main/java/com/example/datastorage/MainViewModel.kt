package com.example.datastorage

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel


class MainViewModel: ViewModel() {
    fun insert(context: Context, u_id: TextView, u_name: TextView, u_email: TextView): Long {
        val databaseHandler = DatabaseHandler(context)

        val id = u_id.text.toString()
        val name = u_name.text.toString()
        val email = u_email.text.toString()

        return if(id.trim()!="" && name.trim()!="" && email.trim()!=""){
            databaseHandler.insertEmployee(Model(Integer.parseInt(id),name, email))
        }else{
            -1
        }
    }

    fun view(context: Context, listView: ListView) {
        val databaseHandler = DatabaseHandler(context)

        val emp: List<Model> = databaseHandler.viewEmployee()
        val empArrayId = Array(emp.size){"0"}
        val empArrayName = Array(emp.size){"null"}
        val empArrayEmail = Array(emp.size){"null"}
        var index = 0
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(context as Activity,empArrayId,empArrayName,empArrayEmail)
        listView.adapter = myListAdapter
    }

    fun update(context: Context) {
        val databaseHandler = DatabaseHandler(context)

        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText

        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateEmail = edtEmail.text.toString()
            //creating the instance of DatabaseHandler class
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){
                Log.i("SQLite", "DatabaseActivity: updateRecord $updateId, $updateName, $updateEmail")
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = databaseHandler.updateEmployee(Model(Integer.parseInt(updateId),updateName, updateEmail))
                if(status > -1){
                    Toast.makeText(context,"record update", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
            }
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
        })
        val b = dialogBuilder.create()
        b.show()
    }

    fun delete(context: Context) {
        val databaseHandler = DatabaseHandler(context)

        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->
            val deleteId = dltId.text.toString()
            Log.i("SQLite", "DatabaseActivity: deleteRecord $deleteId")
            //creating the instance of DatabaseHandler class
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHandler.deleteEmployee(Model(Integer.parseInt(deleteId),"",""))
                if(status > -1){
                    Toast.makeText(context,"record deleted",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
            }
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
}