package com.example.logonrm.carros.ui.novocarro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Carro
import kotlinx.android.synthetic.main.activity_novo_carro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovoCarroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_carro)
        btSalvar.setOnClickListener { cadastrar() }
        btEditar.setOnClickListener { editar() }
    }

    private fun cadastrar() {
        val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
        val carro = Carro(inputPlaca.editText?.text.toString(),
                inputMarca.editText?.text.toString(),
                inputModelo.editText?.text.toString(),
                inputAno.editText?.text.toString().toInt(),
                inputCor.editText?.text.toString())

        api.salvar(carro).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response?.isSuccessful == true) {
                    Toast.makeText(applicationContext, "Salvo com Sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Erro", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e("CARRO", t?.message)
            }
        })
    }

    fun editar() {
        val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
        val carro = Carro(inputPlaca.editText?.text.toString(),
                inputMarca.editText?.text.toString(),
                inputModelo.editText?.text.toString(),
                inputAno.editText?.text.toString().toInt(),
                inputCor.editText?.text.toString())

        api.salvar(carro).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response?.isSuccessful == true) {
                    Toast.makeText(applicationContext, "Alterado com Sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Erro", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e("CARRO", t?.message)
            }
        })
    }
}

