package com.example.logonrm.carros.ui.findplaca

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.logonrm.carros.R

import kotlinx.android.synthetic.main.activity_find_by_placa.*


class FindByPlacaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_by_placa)
        btPesquisarPlaca.setOnClickListener { setPlaca() }
    }

    fun setPlaca(){
        startActivity(Intent(this, FindByPlacaEditActivity::class.java))
    }
}

