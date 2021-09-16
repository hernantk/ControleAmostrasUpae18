
package com.example.intership.ui.list
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.intership.R
import com.example.intership.databinding.NewItemLogBinding
import com.example.intership.domain.dto.LogDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        private val binding = NewItemLogBinding.bind(itemView)

        fun bing(log: LogDto){
            val context = binding.root.context
            val dateFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

            binding.tvDate.text = log.date.format(dateFormater)
            binding.tvEdta.text = context.getString(R.string.textEdta,log.edta)
            binding.tvSoro.text = context.getString(R.string.textSoro,log.soro)
            binding.tvCitrato.text = context.getString(R.string.textCitrato,log.citrato)
            binding.tvFezes.text = context.getString(R.string.textFezes,log.fezes)
            binding.tvUrina.text = context.getString(R.string.textUrina,log.urina)


            displayImageLocalColeta(log.localDeColeta)
            showButtonEdit(log)
            showButtonImage(log)
        }

        val btnUpdateItem: ImageButton
            get() = binding.btnEdit

        val btnOpenImage: ImageButton
            get() = binding.btnImage



        private fun showButtonEdit(log: LogDto){
            if(LocalDate.parse(log.date.toString().removeRange(10,23)) == LocalDate.now()) { binding.btnEdit.visibility=View.VISIBLE }
            else{ binding.btnEdit.visibility=View.GONE }}

        private fun showButtonImage(log: LogDto){
            if(log.imgAmostras!=""){ binding.btnImage.visibility=View.VISIBLE }
            else{ binding.btnImage.visibility=View.GONE }}


        private fun displayImageLocalColeta(localColeta: String){

            when(localColeta){
                "Centro de Saude (18)" -> binding.imgLocaldeColeta.setImageResource(R.drawable.ic_local_de_coleta_centro_de_saude)
                "Upa"-> binding.imgLocaldeColeta.setImageResource(R.mipmap.ic_local_de_coleta_upa_round)
            }

        }




    }}




