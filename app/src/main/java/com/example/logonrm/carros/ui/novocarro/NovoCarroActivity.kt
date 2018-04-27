package com.example.logonrm.carros.ui.novocarro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun cadastrar() {
        var cancel = false

        if(inputPlaca.editText?.text.toString() == null || inputPlaca.editText?.text.toString() == ""){
            cancel = true
            Toast.makeText(applicationContext, getString(R.string.error_campos), Toast.LENGTH_SHORT).show()
        }
        if(inputMarca.editText?.text.toString() == null || inputMarca.editText?.text.toString() == ""){
            cancel = true
            Toast.makeText(applicationContext, getString(R.string.error_campos), Toast.LENGTH_SHORT).show()
        }
        if(inputModelo.editText?.text.toString() == null || inputModelo.editText?.text.toString() == ""){
            cancel = true
            Toast.makeText(applicationContext, getString(R.string.error_campos), Toast.LENGTH_SHORT).show()
        }
        if(inputAno.editText?.text.toString() == null || inputAno.editText?.text.toString() == ""){
            cancel = true
            Toast.makeText(applicationContext, getString(R.string.error_campos), Toast.LENGTH_SHORT).show()
        }
        if(inputCor.editText?.text.toString() == null || inputCor.editText?.text.toString() == ""){
            cancel = true
            Toast.makeText(applicationContext, getString(R.string.error_campos), Toast.LENGTH_SHORT).show()
        }

        if(cancel == false) {
            val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
            val carro = Carro(inputPlaca.editText?.text.toString().toUpperCase(),
                    inputMarca.editText?.text.toString(),
                    inputModelo.editText?.text.toString(),
                    inputAno.editText?.text.toString().toInt(),
                    inputCor.editText?.text.toString())

            api.salvar(carro).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    if (response?.isSuccessful == true) {
                        Toast.makeText(applicationContext, getString(R.string.insert_sucess), Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e("CARRO", t?.message)
                }
            })
        }
    }
}

