package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity() {

    lateinit var binding:ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bRegistrarRegistrar.setOnClickListener {

            //comprobar que ningún campo está vacío
            if(binding.emailRegistro.text.isNotEmpty() && binding.paswordRegistro.text.isNotEmpty()
                && binding.nombre.text.isNotEmpty() && binding.apellidos.text.isNotEmpty()){

                //acceder a firebase con el método createUser... y le pasamos el correo y el password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.emailRegistro.text.toString(), binding.paswordRegistro.text.toString()
                )
                    .addOnCompleteListener{
                        //si el usuario se ha registrado correctamente muestra la pantalla de bienvenida
                        if(it.isSuccessful){
                            startActivity(Intent(this, Bienvenida::class.java))
                        }
                        //si no, nos avisa del error
                        else{Toast.makeText(this, "No se han podido registrar los datos", Toast.LENGTH_SHORT).show()}
                    }

            }else{Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()}
        }
    }
}