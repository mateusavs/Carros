package com.example.logonrm.carros.api

import com.example.logonrm.carros.model.Carro
import com.example.logonrm.carros.model.Users
import retrofit2.Call
import retrofit2.http.*


interface CarroAPI{

    @GET("/carro")
    fun buscarTodos() : Call<List<Carro>>

    @GET("/carro/find/{placa}")
    fun buscarbyPlaca(@Path ("placa") placa: String): Call<Carro>

    @POST("/carro")
    fun salvar(@Body carro: Carro): Call<Void>

    @DELETE("/carro/delete/{placa}")
    fun deletar(@Path("placa") placa: String): Call<Carro>

/*-----------------------------------------------------------*/

    @GET("/users/findUser/{user}")
    fun buscarUser(@Path ("user") user: String): Call<Users>


    @POST("/users")
    fun salvarUser(@Body users: Users): Call<Void>


}