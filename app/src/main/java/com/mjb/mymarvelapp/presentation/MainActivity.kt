package com.mjb.mymarvelapp.presentation

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.mjb.mymarvelapp.R
import com.mjb.mymarvelapp.databinding.ActivityMainBinding
//import com.mjb.mymarvelapp.core.di.component.ViewComponent
import com.mjb.mymarvelapp.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    // TODO borrar dagger
    //override fun initializeInjector(viewComponent: ViewComponent) = viewComponent.inject(this)
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = destination.label
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun showProgressStatus(viewStatus: Int) {
        binding.progress.visibility = viewStatus
    }
}
