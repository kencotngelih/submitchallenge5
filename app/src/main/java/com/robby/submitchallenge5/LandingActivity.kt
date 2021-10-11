package com.robby.submitchallenge5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class LandingActivity : AppCompatActivity() {
    private var name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        val viewPager2 by lazy{this.findViewById<ViewPager2>(R.id.viewPager2)}
        val dots_indicator by lazy{this.findViewById<DotsIndicator>(R.id.dots_indicator)}
        val ivNext by lazy{this.findViewById<ImageView>(R.id.ivNext)}
        val ivPrevious by lazy{this.findViewById<ImageView>(R.id.ivPrevious)}

        val viewPagerAdapter = ViewPagerAdaptor(this) {
            name = it.toString()
        }

        viewPager2.adapter = viewPagerAdapter
        dots_indicator.setViewPager2(viewPager2)

        ivPrevious.setOnClickListener {
            if (viewPager2.currentItem > 0) {
                viewPager2.currentItem = viewPager2.currentItem.plus(-1)
            } else {
                viewPager2.currentItem = viewPager2.currentItem.plus(0)
            }
        }

        ivNext.setOnClickListener {
            if (viewPager2.currentItem < 2) {
                viewPager2.currentItem = viewPager2.currentItem.plus(1)
            } else if (name != "") {
                Intent(this, MainActivity::class.java)
                    .apply {
                        putExtra("Name", name)
                        startActivity(this)
                        finish()
                    }
            } else {
                Toast.makeText(this, "Isi nama terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}