package com.example.intership.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intership.R
import com.example.intership.databinding.ActivityListLogsBinding
import com.example.intership.domain.dto.LogDto
import com.example.intership.ui.register.RegisterLogDialog
import com.example.intership.ui.update.UpdateLogDialog
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    override fun onResume() {
        super.onResume()
        viewModel.listLogs()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_logs,menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun setupEvents() {
        viewModel.logsResult.observe(this){result -> onListSuccess(result)}
        viewModel.logsError.observe(this){onListError()}


        binding.srlLogs.setOnRefreshListener { viewModel.listLogs() }
        binding.floatingActionButton.setOnClickListener{registerLog()}

    }

    private fun setupList() {
        adapter = ListLogsAdapter(::editLog)
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



    private fun onListSuccess(logs:List<LogDto>){
        adapter.logs = logs
        adapter.setNewData()
        binding.srlLogs.isRefreshing = false

    }

    private fun onListError(){
        Snackbar.make(binding.root,"Erro ao Listar logs",Snackbar.LENGTH_LONG).show()
    }
}