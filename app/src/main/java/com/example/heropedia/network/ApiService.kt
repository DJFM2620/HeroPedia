package com.example.heropedia.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//Sirve para hacer la creacion de la consulta y gestionarla
private const val BASE_URL = "http://192.168.1.62:3000/"

private val moshi = Moshi.Builder() //Construimos el Objeto Moshi que usaremos
                         .add(KotlinJsonAdapterFactory())
                         .build()

private val retrofit = Retrofit.Builder()
                               .addConverterFactory(MoshiConverterFactory.create(moshi))
                               .baseUrl(BASE_URL)
                               .build()

interface ApiService {

    //Obtenemos los datos de este path
    @GET("Superheroes")
    suspend fun getSuperheroes(): List<Superhero>//Obtenemos todos los heroes cuando se haga la consulta
}

//Para gestionar correctamente todos las peticiones
//Lazy --> Permite gestionar de forma progresiva
object Api {

    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}

/*
    LiveData --> Clase que contiene datos observables, osea, tiene el ciclo de vida optimizado y permite usar otros componentes,
    fragmentos o servicios que dependen de esta data, lo que hara es observar la data que venga del servicio

    Tiene un patron de diseño observer --> permite tener una actualizacion de cada dato automaticamente y cuando se actualiza se descandena
    ciertas acciones, como por ejemplo el renderizado de las imagenes

 */