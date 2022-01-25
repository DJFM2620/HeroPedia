package com.example.heropedia.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heropedia.network.Api
import com.example.heropedia.network.Superhero
import kotlinx.coroutines.launch
import java.lang.Exception

enum class ApiStatus{LOADING, ERROR, DONE}//Objeto que tiene el status de nuestras consultas

class OverviewViewModel : ViewModel(){

    //Depende de la data que se este consultando, y le damos el ApiStatus para ver si esta retornando algo o no
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status //el _status depende del LiveData y status es solamente para saber el estado de la consulta

    private val _superheroes = MutableLiveData<List<Superhero>>()
    val superheroes: LiveData<List<Superhero>> = _superheroes

    init { //Al inicializar la clase traeremos las fotos

        getSuperheroes()
    }

    private fun getSuperheroes(){

        viewModelScope.launch {//Va a lanzar la consulta

            _status.value = ApiStatus.LOADING //Primero le seteamos el estado de carga ante de tener una respuesta

            try {
                //Evalua si ha traido la info correcta de la clase o si la tengo en el cache, asi que dice que si lo trae de uno de los dos
                _superheroes.value = Api.retrofitService.getSuperheroes()
                _status.value = ApiStatus.DONE

            }catch (e: Exception){

                _status.value = ApiStatus.ERROR
                _superheroes.value = listOf() //En caso de error lo dejamos como un objeto vacio, osea, para no mostrar nada

                Log.i("EL ERROR :,v", "La Causa de la falla es ---> $e")
            }
        }
    }
}