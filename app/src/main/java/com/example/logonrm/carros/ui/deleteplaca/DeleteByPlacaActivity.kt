package com.example.logonrm.carros.ui.deleteplaca

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Carro
import kotlinx.android.synthetic.main.activity_delete_by_placa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteByPlacaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_by_placa)
        btDeletarPlaca.setOnClickListener { deletar() }
    }

    fun deletar() {
        val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
        api.deletar(placaCar.text.toString().toUpperCase()).enqueue(object : Callback<Void> {
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
