package com.example.arjun.googlelogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private lateinit var client: GoogleApiClient
    private lateinit var options: GoogleSignInOptions

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        options = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Scope("https://www.googleapis.com/auth/contacts"))                   // contacts
                .requestScopes(Scope("https://www.googleapis.com/auth/userinfo.profile"))           //basic profile info
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.readonly"))             //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.compose"))              //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.insert"))               //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.labels"))               //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.metadata"))             //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.modify"))               //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.send"))                 //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.settings.basic"))       //email
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.settings.sharing"))     //email
                .build()

        client = GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build()


        login.setOnClickListener {
            signin()
        }

        logout.setOnClickListener {
            signout()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result != null) {

                if (result.isSuccess) {
                    val account = result.signInAccount
                    val name = account!!.displayName
                    val email = account.email
                    val dp = account.photoUrl.toString()

                    user_name.text = name
                    Glide.with(this)
                            .load(dp)
                            .into(user_dp)

                    user_dp.visibility = View.VISIBLE
                    user_name.visibility = View.VISIBLE
                    user_name.visibility = View.VISIBLE
                    logout.visibility = View.VISIBLE

                } else {

                    user_dp.visibility = View.GONE
                    user_name.visibility = View.GONE
                    user_name.visibility = View.GONE
                    logout.visibility = View.GONE
                }
            }

        }
    }

    private fun signin() {

        val intent = Auth.GoogleSignInApi.getSignInIntent(client)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun signout() {

        Auth.GoogleSignInApi.signOut(client).setResultCallback {

            user_dp.visibility = View.GONE
            user_name.visibility = View.GONE
            user_name.visibility = View.GONE
            logout.visibility = View.GONE
        }
    }

    companion object {
        private const val REQUEST_CODE = 9001
    }
}
