package com.robby.submitchallenge5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val main by lazy {findViewById<ConstraintLayout>(R.id.main)}
    val ivVsPlayer by lazy {findViewById<ImageView>(R.id.ivVsPlayer)}
    val tvVsPlayer by lazy {findViewById<TextView>(R.id.tvVsPlayer)}
    val ivVsCpu by lazy {findViewById<ImageView>(R.id.ivVsCpu)}
    val tvVsCpu by lazy {findViewById<TextView>(R.id.tvVsCpu)}
    var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        name = intent.extras?.getString("Name").toString()
        tvVsPlayer.text = "$name vs Pemain 2"
        tvVsCpu.text = "$name vs CPU"

        ivVsPlayer.setOnClickListener {
            val intent1 = Intent(this, VsPlayerActivity::class.java)
            intent1.putExtra("Name", name)
            startActivity(intent1)
            finish()
        }

        ivVsCpu.setOnClickListener {
            val intent1 = Intent(this, VsCpuActivity::class.java)
            intent1.putExtra("Name", name)
            startActivity(intent1)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val snackbar = Snackbar.make(main, "Selamat Datang $name", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Tutup") {
            snackbar.dismiss()
        }.show()
    }
}