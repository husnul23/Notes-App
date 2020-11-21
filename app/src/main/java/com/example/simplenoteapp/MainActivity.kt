package com.example.simplenoteapp

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.security.cert.CertPath
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var path: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_new.setOnClickListener(this)
        button_open.setOnClickListener(this)
        button_save.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun newFile() {
        edit_title.setText("")
        edit_file.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val arrayList = ArrayList<String>()
        val path: File = filesDir
        Collections.addAll(arrayList, *path.list() as Array<String>)
        val items = arrayList.toTypedArray<CharSequence>()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih File yang diinginkan")
        builder.setItems(items) { dialog, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        edit_title.setText(fileModel.filename)
        edit_file.setText(fileModel.data)
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
    }
    
    private fun saveFile() {
        when {
            edit_title.text.toString().isEmpty() -> Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            edit_title.text.toString().isEmpty() -> Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = edit_title.text.toString()
                val text = edit_file.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving", Toast.LENGTH_SHORT).show()
            }
        }
    }
}