package com.example.autenticacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.autenticacion.databinding.ActivityBienvenidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Bienvenida : AppCompatActivity() {
    lateinit var binding: ActivityBienvenidaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()

        binding.botonCerrarsesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            //volver a la main activity
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.botonGuardarCoche.setOnClickListener {
            if (binding.marcaCoche.text.isNotEmpty() &&
                binding.matriculaCoche.text.isNotEmpty() &&
                binding.modeloCoche.text.isNotEmpty()&&
                binding.colorCoche.text.isNotEmpty()){

                db.collection("coches").add(mapOf(
                    "color" to binding.colorCoche.text,
                    "marca" to binding.marcaCoche.text,
                    "matricula" to binding.matriculaCoche.text,
                    "modelo" to binding.modeloCoche.text
                ))

                    .addOnSuccessListener { documento ->
                        Toast.makeText(this, "Nuevo coche añadido con el id: ${documento.id}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error en la inserción del coche", Toast.LENGTH_SHORT).show()
                    }

            }else{
                Toast.makeText(this , "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }





}