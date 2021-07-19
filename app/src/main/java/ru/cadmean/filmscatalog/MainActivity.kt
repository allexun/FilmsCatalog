package ru.cadmean.filmscatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.main_navigation)
        navController.navigate(R.id.action_loadingFragment_to_filmsListFragment)
    }
}