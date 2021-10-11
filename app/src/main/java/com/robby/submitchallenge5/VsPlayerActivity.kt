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

class VsPlayerActivity : AppCompatActivity() {

    var name = ""
    val controller = Controller()
    var readyPlayer1 = false
    var readyPlayer2 = false

    val ivPlyTitle by lazy {this.findViewById<ImageView>(R.id.ivPlyTitle)}
    val ivPlyBatu by lazy {this.findViewById<ImageView>(R.id.ivPlyBatu)}
    val ivPlyKertas by lazy {this.findViewById<ImageView>(R.id.ivPlyKertas)}
    val ivPlyGunting by lazy {this.findViewById<ImageView>(R.id.ivPlyGunting)}
    val ivPlyBatuCpu by lazy {this.findViewById<ImageView>(R.id.ivPlyBatuCpu)}
    val ivPlyKertasCpu by lazy {this.findViewById<ImageView>(R.id.ivPlyKertasCpu)}
    val ivPlyGuntingCpu by lazy {this.findViewById<ImageView>(R.id.ivPlyGuntingCpu)}
    val ivPlyRefresh by lazy {this.findViewById<ImageView>(R.id.ivPlyRefresh)}
    val ivPlyClose by lazy {this.findViewById<ImageView>(R.id.ivPlyClose)}
    val tvPlyPlayer by lazy {this.findViewById<TextView>(R.id.tvPlyPlayer)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vs_player)
        Glide.with(this)
            .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
            .into(ivPlyTitle)

        name = intent.extras?.getString("Name").toString()
        tvPlyPlayer.text = name

        var pemain1 = mutableListOf(ivPlyBatu, ivPlyKertas,ivPlyGunting)
        var pemain2 = mutableListOf(ivPlyBatuCpu, ivPlyKertasCpu, ivPlyGuntingCpu)
        var pilihan = mutableListOf("batu", "kertas","gunting")

        pemain1.forEach {
            it.setOnClickListener {
                it.setBackgroundResource(R.drawable.ic_background)
                controller.setPlayer1(pilihan[pemain1.indexOf(it)])
                Toast.makeText(this, "${tvPlyPlayer.text} sudah memilih.", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "${tvPlyPlayer.text} memilih ${controller.getPlayer1()}")
                readyPlayer1 = true
                if(readyPlayer1 && readyPlayer2){
                    tvResult()
                }
                pemain1.forEach {it.isClickable = false}
            }
        }

        pemain2.forEach {
            it.setOnClickListener {
                it.setBackgroundResource(R.drawable.ic_background)
                ivPlyBatuCpu.isClickable = false
                ivPlyKertasCpu.isClickable = false
                ivPlyGuntingCpu.isClickable = false
                controller.setPlayer2(pilihan[pemain2.indexOf(it)])
                Toast.makeText(this, "Pemain 2 sudah memilih.", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Pemain 2 memilih ${controller.getPlayer2()}")
                readyPlayer2 = true
                if(readyPlayer1 && readyPlayer2){
                    tvResult()
                }
                pemain2.forEach {it.isClickable = false}
            }
        }

        ivPlyRefresh.setOnClickListener {
            reset()
        }

        ivPlyClose.setOnClickListener {
            menu()
        }
    }

    private fun tvResult() {
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
                pemenang.text = "Pemain 2\nMENANG!"
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
        var pemain1 = mutableListOf(ivPlyBatu, ivPlyKertas,ivPlyGunting)
        var pemain2 = mutableListOf(ivPlyBatuCpu, ivPlyKertasCpu, ivPlyGuntingCpu)
        readyPlayer1 = false
        readyPlayer2 = false

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
}