package com.example.logonrm.carros.ui.listacarros

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Carro
import kotlinx.android.synthetic.main.activity_lista_carro.*
import kotlinx.android.synthetic.main.erro.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaCarroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_carro)
        carregarDados()
    }


    fun carregarDados() {
        val api = RetrofitClient
                .getInstance()
                .create(CarroAPI::class.java)

        loading.visibility = View.VISIBLE

        api.buscarTodos().enqueue(object : Callback<List<Carro>> {
            override fun onFailure(call: Call<List<Carro>>?,
                                   t: Throwable?) {
                containerErro.visibility = View.VISIBLE
                tvMensagemErro.text = t?.message
                loading.visibility = View.GONE

            }

            override fun onResponse(call: Call<List<Carro>>?,
                                    response: Response<List<Carro>>?) {
                containerErro.visibility = View.GONE
                tvMensagemErro.text = ""

                if (response?.isSuccessful == true) {
                    setupLista(response?.body())
                } else {
                    containerErro.visibility = View.VISIBLE
                    tvMensagemErro.text = response?.errorBody()?.charStream()?.readText()
                }
                loading.visibility = View.GONE
            }
        })
    }

    fun setupLista(carros: List<Carro>?) {
        carros.let {
            rvCarros.adapter = ListaCarrosAdapter(carros!!, applicationContext)
            val layoutManager = LinearLayoutManager(applicationContext)
            rvCarros.layoutManager = layoutManager
        }
    }
}
