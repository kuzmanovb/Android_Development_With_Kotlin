package com.example.homeworkandroidvisual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.homeworkandroidvisual.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var count = 0
    private var pictures = listOf("weather_cloudy","weather_fair_nigth", "weather_rain", "weather_snow", "weather_sunny")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countNumber = count

        binding.changePictureButton.setOnClickListener { view ->
            // increase count and add to countNumber
            binding.countNumber = ++count

            // get random picture
            val randomPicture = pictures.random()
            val uri = "@drawable/$randomPicture"
            val imageId = resources.getIdentifier(uri, null, packageName);
            val res = ResourcesCompat.getDrawable(resources, imageId, null)

            // change picture
            binding.imageView.setImageDrawable(res)
        }
    }
}