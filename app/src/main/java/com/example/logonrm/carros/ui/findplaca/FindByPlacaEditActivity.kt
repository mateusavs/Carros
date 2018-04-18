package com.example.logonrm.carros.ui.findplaca

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Carro
import kotlinx.android.synthetic.main.activity_find_by_placa_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindByPlacaEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_by_placa_edit)
        exibir()
        btEdit.setOnClickListener { editar() }
        btExcluir.setOnClickListener { deletar() }
    }

    fun exibir(){
        var placa: String = intent.getStringExtra("placa")
        var marca: String = intent.getStringExtra("marca")
        var modelo: String = intent.getStringExtra("modelo")
        var ano: String = intent.getStringExtra("ano")
        var cor: String = intent.getStringExtra("cor")
        inputPlaca.editText?.setText(placa)
        inputMarca.editText?.setText(marca)
        inputModelo.editText?.setText(modelo)
        inputAno.editText?.setText(ano)
        inputCor.editText?.setText(cor)

    }

    fun deletar() {
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

    private fun editar() {
        val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
        val carro = Carro(inputPlaca.editText?.text.toString().toUpperCase(),
                inputMarca.editText?.text.toString(),
                inputModelo.editText?.text.toString(),
                inputAno.editText?.text.toString().toInt(),
                inputCor.editText?.text.toString())

        api.salvar(carro).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response?.isSuccessful == true) {
                    Toast.makeText(applicationContext, "Changed Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.e("CARRO", t?.message)
            }
        })
    }

}
