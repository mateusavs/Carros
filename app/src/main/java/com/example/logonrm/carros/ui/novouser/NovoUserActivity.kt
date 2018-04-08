package com.example.logonrm.carros.ui.novouser


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Users
import com.example.logonrm.carros.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_novo_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NovoUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_user)
        btSalvar.setOnClickListener { cadastrar() }
    }
    private fun cadastrar() {
            val api = RetrofitClient.getInstance().create(CarroAPI::class.java)
            val user = Users(inputUser.editText?.text.toString(),
                    inputPassword.editText?.text.toString(),
                    inputMail.editText?.text.toString(),
                    inputName.editText?.text.toString())

            api.salvarUser(user).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    if (response?.isSuccessful == true) {
                        Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
                        limparCampos()
                    } else {
                        Toast.makeText(applicationContext, "Erro", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e("USERS", t?.message)
                }
            })
        }

    private fun limparCampos(){
        inputUser.editText?.setText("")
        inputPassword.editText?.setText("")
        inputMail.editText?.setText("")
        inputName.editText?.setText("")
        startActivity(Intent(this, MainActivity::class.java))
    }
}





