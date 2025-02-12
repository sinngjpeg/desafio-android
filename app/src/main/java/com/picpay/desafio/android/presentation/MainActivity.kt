package com.picpay.desafio.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navHostFragment.navController


//        // Adiciona o fragmento inicial ao contêiner, se ainda não foi adicionado
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(binding.fragmentContainerView.id, UserFragment()) // Substitua pelo seu Fragment inicial
//                .commit()
//        }
    }
}
