package com.example.heropedia.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.heropedia.databinding.GridViewItemBinding
import com.example.heropedia.network.Superhero

class SuperheroGridAdapter : ListAdapter<Superhero, SuperheroGridAdapter.SuperheroViewholder>(DiffCallback) {
    //DifCallBack --> Para saber cuando hara la llamada del servicio

    //Permite saber que datos vamos a renderizar
    class SuperheroViewholder(private val binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root){//La raiz de los datos

        fun bind(superheroes : Superhero){

            binding.superhero = superheroes //Va setear las fotos que reciba de la consulta
            binding.executePendingBindings() //Ejecuta todos los datos que esten pendientes de pasar por el root
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Superhero>(){

        //Para saber que objetos reemplazar a la hora de hacer la consulta mediante lo que hay en el cache
        override fun areItemsTheSame(oldItem: Superhero, newItem: Superhero): Boolean {

            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Superhero, newItem: Superhero): Boolean {

            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewholder {

        return SuperheroViewholder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SuperheroViewholder, position: Int){

        val superhero = getItem(position)

        holder.bind(superhero)
    }
}