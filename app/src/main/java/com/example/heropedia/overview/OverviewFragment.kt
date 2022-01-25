package com.example.heropedia.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heropedia.databinding.FragmentOverviewBinding

class OverviewFragment: Fragment() {

    private val viewModel : OverviewViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstaceState: Bundle?): View? {

        val binding = FragmentOverviewBinding.inflate(inflater) //Renderizamos el layout que usaremos

        binding.lifecycleOwner = this //Hace referencia que este es el ciclo de vida de esta vista
        binding.viewModel = viewModel
        binding.superheroGrid.adapter = SuperheroGridAdapter()

        //Como se usa un fragment no es necesario que se cree la vista directamente, solo lo añadimos a la raiz de los fragmentos
        return binding.root
    }
}