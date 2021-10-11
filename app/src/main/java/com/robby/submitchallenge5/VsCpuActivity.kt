package com.robby.submitchallenge5

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide

class VsCpuActivity : AppCompatActivity() {

    val ivCpuTitle by lazy {this.findViewById<ImageView>(R.id.ivCpuTitle)}
    val ivCpuBatu by lazy {this.findViewById<ImageView>(R.id.ivCpuBatu)}
    val ivCpuKertas by lazy {this.findViewById<ImageView>(R.id.ivCpuKertas)}
    val ivCpuGunting by lazy {this.findViewById<ImageView>(R.id.ivCpuGunting)}
    val ivCpuBatuCpu by lazy {this.findViewById<ImageView>(R.id.ivCpuBatuCpu)}
    val ivCpuKertasCpu by lazy {this.findViewById<ImageView>(R.id.ivCpuKertasCpu)}
    val ivCpuGuntingCpu by lazy {this.findViewById<ImageView>(R.id.ivCpuGuntingCpu)}
    val ivCpuRefresh by lazy {this.findViewById<ImageView>(R.id.ivCpuRefresh)}
    val ivCpuClose by lazy {this.findViewById<ImageView>(R.id.ivCpuClose)}
    val tvCpuPlayer by lazy {this.findViewById<TextView>(R.id.tvCpuPlayer)}

    var name = ""
    val controller = Controller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vs_cpu)
        Glide.with(this)
            .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
            .into(ivCpuTitle)

        name = intent.extras?.getString("Name").toString()
        tvCpuPlayer.text = name

        var pemain1 = mutableListOf(ivCpuBatu, ivCpuKertas,ivCpuGunting)
        var pilihan = mutableListOf("batu", "kertas", "gunting")

        pemain1.forEach {
            it.setOnClickListener {
                it.setBackgroundResource(R.drawable.ic_background_click)
                ivCpuBatu.isClickable = false
                ivCpuGunting.isClickable = false
                ivCpuKertas.isClickable = false
                Toast.makeText(this, "${tvCpuPlayer.text} memilih ${pilihan[pemain1.indexOf(it)]}", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "${tvCpuPlayer.text} memilih ${pilihan[pemain1.indexOf(it)]}")
                pemain1.forEach { it.isClickable = false }
                controller.setPlayer1(pilihan[pemain1.indexOf(it)])
                acak()
                tvResult()
            }
        }

        ivCpuRefresh.setOnClickListener {
            reset()
        }

        ivCpuClose.setOnClickListener {
            menu()
        }


    }
    fun tvResult(){
        val dialogBuilder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null, false)

        val btnMenuUtama by lazy { view.findViewById<Button>(R.id.btnMenuUtama) }
        val pemenang by lazy{view.findViewById<TextView>(R.id.tvResult)}
        val btnPlayAgain by lazy{view.findViewById<Button>(R.id.btnPlayAgain)}

        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()
        dialog.setCancelable(false)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        when (controller.logicGame()){
            1 ->{
                pemenang.text = "${name}\nMENANG!"
            }
            2 ->{
                pemenang.text = "CPU\nMENANG!"
            }

        }
        btnMenuUtama.setOnClickListener {
            dialog.dismiss()
            menu()
        }
        btnPlayAgain.setOnClickListener {
            dialog.dismiss()
            reset()
        }
        dialog.show()

    }
    fun reset(){
        var pemain1 = mutableListOf(ivCpuBatu, ivCpuKertas,ivCpuGunting)
        var pemain2 = mutableListOf(ivCpuBatuCpu, ivCpuKertasCpu,ivCpuGuntingCpu)

        pemain1.forEach {
            it.setBackgroundResource(R.drawable.ic_background)
            it.isClickable = true
        }

        pemain2.forEach {
            it.setBackgroundResource(R.drawable.ic_background)
            it.isClickable = true
        }
    }
    fun menu(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Name", name)
        startActivity(intent)
        finish()
    }

    fun acak(){
        var pemain2 = mutableListOf(ivCpuBatuCpu, ivCpuKertasCpu,ivCpuGuntingCpu)
        var pilihan = mutableListOf("batu", "kertas", "gunting")
        val result = pemain2.random()
        result.setBackgroundResource(R.drawable.ic_background_click)
        pemain2.forEach { it.isClickable = false }
        Toast.makeText(this, "CPU memilih ${pilihan[pemain2.indexOf(result)]}", Toast.LENGTH_SHORT).show()
        controller.setPlayer2(pilihan[pemain2.indexOf(result)])
    }
}