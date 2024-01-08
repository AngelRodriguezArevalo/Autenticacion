package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bLogin.setOnClickListener {
            login()
        }

        binding.bRegistrar.setOnClickListener {
            registro()
        }
    }

    private fun registro() {
        binding.bRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun login() {
        if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()) {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            )

                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this, Bienvenida::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Correo o contraseña incorrecto", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()
        }
    }
}