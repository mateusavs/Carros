package com.example.logonrm.carros.ui.Login

import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView

import java.util.ArrayList
import android.content.Intent
import com.example.logonrm.carros.R
import com.example.logonrm.carros.ui.main.MainActivity
import com.example.logonrm.carros.ui.novouser.NovoUserActivity

import kotlinx.android.synthetic.main.activity_login.*

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

        btn_entrar.setOnClickListener { attemptLogin() }
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

        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        if (TextUtils.isEmpty(userStr)) {
            user.error = getString(R.string.error_field_required)
            focusView = user
            cancel = true
        }

        if (cancel == false){
        startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    private fun attempCadastrar (){
        startActivity(Intent(this, NovoUserActivity::class.java))
    }

    private fun attempSair (){
        finish()
        System.exit(0)
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), LoginActivity.ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE),

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
    }

    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
        val emails = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            emails.add(cursor.getString(LoginActivity.ProfileQuery.ADDRESS))
            cursor.moveToNext()
        }
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
