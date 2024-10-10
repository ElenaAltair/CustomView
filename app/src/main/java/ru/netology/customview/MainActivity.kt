package ru.netology.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // заполним данными наше view
        /*
        binding.statsView.data = listOf(
            0.25F,
            0.25F,
            0.25F,
            0.25F,
        )*/
        binding.statsView.data = listOf(
            500F,
            500F,
            500F,
            500F,
        )
    }
}