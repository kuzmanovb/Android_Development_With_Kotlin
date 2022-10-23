package com.example.homeworkfirstproject

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.homeworkfirstproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var count: Int = 0
    private lateinit var previousInput: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.saveTextButton.setOnClickListener { view ->
            // get text from input field
            val inputText = binding.inputTextField.text.toString()
            if (inputText.isNotEmpty()) {
                // save previous text
                previousInput = binding.textViewContent.text.toString()
                // increase count
                count++
                // set input text to text field
                binding.textViewContent.text = "${count}. $inputText"
                // clear input fieldTest
                binding.inputTextField.text = null
            } else {
                Snackbar.make(view, "Please write any text", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        binding.resetButton.setOnClickListener { view ->
            val defaultText = getString(R.string.initial_text_textview)
            count = 0
            binding.textViewContent.text = defaultText
            binding.inputTextField.text = null
        }

        binding.undoButton.setOnClickListener { view ->
            val currentText = binding.textViewContent.text.toString()
            val defaultText = getResources().getString(R.string.initial_text_textview)
            if (currentText != defaultText) {
                count--
                binding.textViewContent.text = previousInput;
            }
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}