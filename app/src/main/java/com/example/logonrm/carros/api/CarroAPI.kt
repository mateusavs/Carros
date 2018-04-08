package com.example.logonrm.carros.api

import com.example.logonrm.carros.model.Carro
import com.example.logonrm.carros.model.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface CarroAPI{

    @GET("/carro")
    fun buscarTodos() : Call<List<Carro>>

    @POST("/carro")
    fun salvar(@Body carro: Carro): Call<Void>

    @GET("/users")
    fun buscarTodosOsUsers(@Body users: Users): Call<Void>

    @POST("/users")
    fun salvarUser(@Body users: Users): Call<Void>


}