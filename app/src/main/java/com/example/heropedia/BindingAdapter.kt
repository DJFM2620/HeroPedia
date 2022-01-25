package com.example.heropedia

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.heropedia.network.Superhero
import com.example.heropedia.overview.ApiStatus
import com.example.heropedia.overview.SuperheroGridAdapter

// ? --> Significa que es Opcional
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Superhero>?){

    val adapter = recyclerView.adapter as SuperheroGridAdapter //Que es el contenedor de la grilla
    adapter.submitList(data)
}

//Declarar los datos a las var que estamos usando
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){ // Es opcional porque puede que no responda el servicio

    //imgUrl? debe contener un String, y este ultimo debe contener el valor del ImgURl
    imgUrl?.let {

        //toUri --> Le damos un formato tipo URL
        //buildUp --> Para que entienda que es una peticion HTTP en esa uri que le damos
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        imgView.load(imgUri){//Cuando este cargando le damos una imagen por default

            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_baseline_broken_image_24)
        }
    }
}

@BindingAdapter("ApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?){//Le seteamos una imagen por cada estado

    when(status){

        ApiStatus.LOADING -> {

            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {

            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.superhero_loading)
        }
        ApiStatus.DONE -> {

            statusImageView.visibility = View.GONE
        }
    }
}

