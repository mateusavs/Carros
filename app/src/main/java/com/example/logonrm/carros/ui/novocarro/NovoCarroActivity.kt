package com.example.logonrm.carros.ui.novocarro

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Carro
import com.example.logonrm.carros.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_novo_carro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovoCarroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_carro)
        btSalvar.setOnClickListener { cadastrar() }
        btExcluir.setOnClickListener { excluir() }
    }

    private fun cadastrar() {
        val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
        val carro = Carro(inputPlaca.editText?.text.toString().toUpperCase(),
                inputMarca.editText?.text.toString(),
                inputModelo.editText?.text.toString(),
                inputAno.editText?.text.toString().toInt(),
                inputCor.editText?.text.toString())

        api.salvar(carro).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response?.isSuccessful == true) {
                    Toast.makeText(applicationContext, "Created Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Erro", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e("CARRO", t?.message)
            }
        })
    }

    fun excluir() {
        val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
        api.deletar(inputPlaca.editText?.text.toString()).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response?.isSuccessful == true) {
                    Toast.makeText(applicationContext, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                    finish()
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

