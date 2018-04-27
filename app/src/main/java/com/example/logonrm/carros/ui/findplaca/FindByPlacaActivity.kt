package com.example.logonrm.carros.ui.findplaca

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Carro

import kotlinx.android.synthetic.main.activity_find_by_placa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FindByPlacaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_by_placa)
        btPesquisarPlaca.setOnClickListener { setPlaca() }
    }

    fun setPlaca() {

        var cancel =  false

        var placa = tvPlacaCar.text.toString().toUpperCase()

        if (placa == null || placa  == ""){
            cancel = true
            tvPlacaCar.error = getString(R.string.error_field_required)
        }
        if (cancel == false) {
            val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
            api.buscarbyPlaca(placa).enqueue(object : Callback<Carro> {

                override fun onResponse(call: Call<Carro>?,
                                        response: Response<Carro>?) {
                    if (response?.isSuccessful == true) {
                        setupLista(response?.body()!!)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.error_car), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Carro>?, t: Throwable?) {
                    Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }


            })
        }
    }

    fun setupLista(carro: Carro){
        carro.let {
            var intent: Intent = Intent(this, FindByPlacaEditActivity::class.java)
            var bundle: Bundle = Bundle()
            var placa: String = carro.placa
            var marca: String  = carro.marca
            var modelo: String  = carro.modelo
            var ano: String = carro.ano.toString()
            var cor: String  = carro.cor


            bundle.putString("placa", placa)
            bundle.putString("marca", marca)
            bundle.putString("modelo", modelo)
            bundle.putString("ano", ano)
            bundle.putString("cor", cor)
            finish()
            intent.putExtras(bundle)
            startActivity(intent)

        }

    }
}

