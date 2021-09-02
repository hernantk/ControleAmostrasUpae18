package com.example.intership.ui.list

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intership.R
import com.example.intership.databinding.ActivityListLogsBinding
import com.example.intership.domain.dto.LogDto
import com.example.intership.ui.image.ImageActivity
import com.example.intership.ui.register.RegisterLogDialog
import com.example.intership.ui.update.UpdateLogDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.util.*


class ListLogsActivity : AppCompatActivity() {
    
    private val binding : ActivityListLogsBinding by lazy { ActivityListLogsBinding.inflate(layoutInflater) }

    private val viewModel:ListLogsViewModel by viewModel()
    private lateinit var adapter: ListLogsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupList()
        setupEvents()

        viewModel.listLogs()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_logs,menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione a Data")
                .build()
        datePicker.addOnPositiveButtonClickListener {day->
            val dateString: String = DateFormat.format("yyyy-MM-dd", Date(day)).toString()
            adapter.setNewData(LocalDate.parse(dateString))
        }
        when(item.itemId){
            R.id.idCalendar -> {
                datePicker.show(supportFragmentManager, "tag")


            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setupEvents() {
        viewModel.logsResult.observe(this){result -> onListSuccess(result)}
        viewModel.logsError.observe(this){onListError()}


        binding.srlLogs.setOnRefreshListener { viewModel.listLogs() }
        binding.floatingActionButton.setOnClickListener{registerLog()}

    }

    private fun setupList() {
        adapter = ListLogsAdapter(::editLog,::openInGallery)
        binding.rvLogs.adapter = adapter
        binding.rvLogs.layoutManager = LinearLayoutManager(this)

    }

    private fun registerLog(){
        val dialog = RegisterLogDialog()
        dialog.show(supportFragmentManager,"Haha")
        dialog.onSave={
            viewModel.listLogs()
        }
    }

    private fun editLog(position: Int) {
        val dialog = UpdateLogDialog()
        dialog.show(supportFragmentManager,"Haha")
        dialog.log = adapter.logs[position]
        dialog.onUpdate={
            viewModel.listLogs()
        }
    }
    private fun openInGallery(position: Int) {
        //val image = decode(adapter.logs[position].imgAmostras, DEFAULT)
        //val bitmap = BitmapFactory.decodeByteArray(image,0, image.size)
        //val intent = Intent().also { intent ->  intent.action=Intent.ACTION_VIEW
          //                         intent.data = getImageUri(this,bitmap) }
        val log = adapter.logs[position]
        val intent = Intent(this, ImageActivity::class.java).putExtra("LOG",log)
        startActivity(intent)
    }



    private fun onListSuccess(logs:List<LogDto>){
        adapter.mlogs = logs
        adapter.setNewData()
        binding.srlLogs.isRefreshing = false

    }

    private fun onListError(){
        Snackbar.make(binding.root,"Erro ao Listar logs",Snackbar.LENGTH_LONG).show()
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}