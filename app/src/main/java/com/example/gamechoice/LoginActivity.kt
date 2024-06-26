package com.example.gamechoice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gamechoice.databinding.ActivityLoginBinding
import com.example.sharedpreferences071123.Preferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private var responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Log.d("Register_0", it.resultCode.toString())
        Log.d("register_01", it.data.toString())
        if (it.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val cuenta = task.getResult(ApiException::class.java)

                if (cuenta != null) {
                    val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credenciales)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                cargarActivity()
                            } else {
                                Log.d("Register_1", it.exception.toString())
                            }
                        }
                        .addOnFailureListener {
                            Log.d("Register_2", it.message.toString())
                        }
                }
            }catch (e : ApiException){
                Log.d("Register_3", e.message.toString())

            }
        }else{
            Toast.makeText(this, "El usuario ha cancelado", Toast.LENGTH_SHORT).show()
        }
    }
    private var email=""
    private var pass=""
    private lateinit var pref: Preferences
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = Preferences(this)
        auth = Firebase.auth
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        val usuario= auth.currentUser
        if (usuario!=null){
            cargarActivity()
        }
    }



    private fun setListeners() {
        binding.btRegister.setOnClickListener{
            if(comprobarCampo()){
                registroBasico()
            }
        }

        binding.btLogin.setOnClickListener{
            if (comprobarCampo()){
                loginBasico()
            }
        }

        binding.btLoginGoogle.setOnClickListener{
            loginGoogle()
        }
    }

    private fun loginGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(this, googleConf)

        googleClient.signOut()

        responseLauncher.launch(googleClient.signInIntent)
    }

    private fun loginBasico() {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    cargarActivity()
                }else{

                }
            }
    }

    private fun cargarActivity() {
            startActivity(Intent(this, MainActivity::class.java))
    }

    private fun registroBasico() {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    loginBasico()
                }else{

                }
            }
    }

    private fun comprobarCampo(): Boolean {
        email=binding.etEmail.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error="Introduce una direccion de email válida00"
            return false
        }
        pass=binding.etPassword.text.toString().trim()
        if(pass.length<6){
            binding.etPassword.error="Error la contraseña debe tener al menos 6 caracteres"
            return false
        }
        return true
    }
}