package com.example.intership.ui.list

import android.graphics.BitmapFactory
import android.text.format.DateFormat
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.intership.R
import com.example.intership.databinding.ItemLogBinding
import com.example.intership.domain.dto.LogDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ListLogsAdapter(val goUpdateLog: (Int) -> Unit,val goOpenImage: (Int) -> Unit) : RecyclerView.Adapter<ListLogsAdapter.LogsViewHolder>() {

    var mlogs:List<LogDto> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var logs:List<LogDto> = mlogs




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.new_item_log,parent,false)
        return LogsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        holder.bing(logs[position])
        holder.btnUpdateItem.setOnClickListener{ goUpdateLog(position)}
        holder.btnOpenImage.setOnClickListener{goOpenImage(position)}
    }

    override fun getItemCount() = logs.size

    fun setNewData(){
        logs = mlogs.filter { logDto -> LocalDate.parse(logDto.date.toString().removeRange(10,23)) == LocalDate.now()  }
        notifyDataSetChanged()
    }
    fun setNewData(date:LocalDate){
        logs = mlogs.filter { logDto -> LocalDate.parse(logDto.date.toString().removeRange(10,23)) == date  }
        notifyDataSetChanged()
    }


    inner class LogsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemLogBinding.bind(itemView)

        fun bing(log: LogDto){
            val dateFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            binding.tvDate.text = log.date.format(dateFormater)
            showIfHasAny(log)
            binding.tvLocaldeColeta.text = log.localDeColeta
            if(log.imgAmostras==""){
                binding.btnImage.visibility=View.GONE
            }
            else{
                binding.btnImage.visibility=View.VISIBLE
            }


        }
        val btnUpdateItem: ImageButton
            get() = binding.btnEdit

        val btnOpenImage: ImageButton
            get() = binding.btnImage


        fun showIfHasAny(log:LogDto){
            val context = binding.root.context
                binding.tvEdta.text = context.getString(R.string.textEdta,log.edta)
                binding.tvSoro.text = context.getString(R.string.textSoro,log.soro)
                binding.tvCitrato.text = context.getString(R.string.textCitrato,log.citrato)
                binding.tvFezes.text = context.getString(R.string.textFezes,log.fezes)
                binding.tvUrina.text = context.getString(R.string.textUrina,log.urina)
                binding.tvEdta.visibility = View.VISIBLE
                binding.tvSoro.visibility = View.VISIBLE
                binding.tvCitrato.visibility = View.VISIBLE
                binding.tvFezes.visibility = View.VISIBLE
                binding.tvUrina.visibility = View.VISIBLE


            }
    }
    fun hasAny(log:LogDto) :Boolean{
        return log.citrato=="0" && log.edta=="0" && log.fezes=="0" && log.soro=="0" && log.urina=="0"
    }
    }




