package com.example.logonrm.carros.ui.listacarros

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.logonrm.carros.R
import com.example.logonrm.carros.ui.deleteplaca.DeleteByPlacaActivity
import com.example.logonrm.carros.ui.findplaca.FindByPlacaActivity
import com.example.logonrm.carros.ui.novocarro.NovoCarroActivity
import kotlinx.android.synthetic.main.fragment_lista_carros.*

class ListaCarrosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_lista_carros, container, false)


    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_findAll.setOnClickListener { attempFindAll() }
        btn_findCarByPlaca.setOnClickListener { attempFindPlaca() }
        btn_cadastrarCar.setOnClickListener { attempCadastrarCar() }
        btn_deletarCar.setOnClickListener { attempDeletarCar() }
    }

    private fun attempFindAll(){
        val intent: Intent = Intent(activity, ListaCarroActivity::class.java)
        startActivity(intent)
    }

    private fun attempFindPlaca(){
        val intent: Intent = Intent(activity, FindByPlacaActivity::class.java)
        startActivity(intent)
    }

    private fun attempCadastrarCar(){
        val intent: Intent = Intent(activity, NovoCarroActivity::class.java)
        startActivity(intent)
    }

    private fun attempDeletarCar(){
        val intent: Intent = Intent(activity, DeleteByPlacaActivity::class.java)
        startActivity(intent)
    }
}

