package com.example.logonrm.carros.ui.Login

import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.logonrm.carros.R
import com.example.logonrm.carros.api.CarroAPI
import com.example.logonrm.carros.api.RetrofitClient
import com.example.logonrm.carros.model.Users
import com.example.logonrm.carros.ui.main.MainActivity
import com.example.logonrm.carros.ui.novouser.NovoUserActivity

import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        btn_login.setOnClickListener { attemptLogin() }
        btn_cadastrar.setOnClickListener { attempCadastrar() }
        btn_sair.setOnClickListener { attempSair() }
    }

    private fun attemptLogin() {

        user.error = null
        password.error = null

        val userStr = user.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null


        if (TextUtils.isEmpty(userStr)) {
            user.error = getString(R.string.error_field_required)
            focusView = user
            cancel = true
        }

        val api = RetrofitClient.getInstance().create(CarroAPI::class.java)

        api.buscarUser(userStr).enqueue(object : Callback<Users>{
            override fun onResponse(call: Call<Users>?, response: Response<Users>?) {
                    if (response?.isSuccessful == true) {
                        setupLista(response?.body()!!)
                    }
                     else {
                        Toast.makeText(applicationContext, "User Not Exist", Toast.LENGTH_SHORT).show()
                        cancel = true
                    }
                }
            override fun onFailure(call: Call<Users>?, t: Throwable?) {
                Log.e("USERS", t?.message)
            }
        })

    }

    fun setupLista(users:Users){
        users.let {
            users.user
            users.password
            var cancel = true
            if (user.text.toString() == users.user && password.text.toString() == users.password){
                cancel = false
            }
            if (cancel == false) {
                Toast.makeText(applicationContext, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(applicationContext, "Invalid User or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun attempCadastrar() {
        startActivity(Intent(this, NovoUserActivity::class.java))
    }

    private fun attempSair() {
        finish()
        System.exit(0)
    }






    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(this,
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), LoginActivity.ProfileQuery.PROJECTION,
                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE),
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
    }
    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {

    }
    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {

    }

    object ProfileQuery {
        val PROJECTION = arrayOf(
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY)
        val ADDRESS = 0
        val IS_PRIMARY = 1
    }


}
