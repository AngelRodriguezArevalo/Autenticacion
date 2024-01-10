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

        // AÑADIR UN NUEVO COCHE CONOCIENDO SU IDENTIFICADOR id (matrícula)

        binding.botonGuardarCoche.setOnClickListener {
            if (binding.marcaCoche.text.isNotEmpty() &&
                binding.matriculaCoche.text.isNotEmpty() &&
                binding.modeloCoche.text.isNotEmpty()&&
                binding.colorCoche.text.isNotEmpty()){

                db.collection("coches").document(binding.matriculaCoche.text.toString())
                    .set(mapOf(
                        "color" to binding.colorCoche.text.toString(),
                        "marca" to binding.marcaCoche.text.toString(),
                        "modelo" to binding.modeloCoche.text.toString()
                    ))
                Toast.makeText(this , "El coche se ha registrado con éxito", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this , "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            }
        }

        // ELIMINAR UN REGISTRO SABIENDO SU ID (matricula)

        binding.botonEliminarCoche.setOnClickListener {
            db.collection("coches")
                .document(binding.matriculaCoche.text.toString())
                .delete()
        }

        /*
        binding.botonGuardarCoche.setOnClickListener {
            if (binding.marcaCoche.text.isNotEmpty() &&
                binding.matriculaCoche.text.isNotEmpty() &&
                binding.modeloCoche.text.isNotEmpty()&&
                binding.colorCoche.text.isNotEmpty()){

                db.collection("coches").add(mapOf(
                    "color" to binding.colorCoche.text.toString(),
                    "marca" to binding.marcaCoche.text.toString(),
                    "matricula" to binding.matriculaCoche.text.toString(),
                    "modelo" to binding.modeloCoche.text.toString()
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


         */
        binding.botonEditarCoche.setOnClickListener {
            db.collection("coches")
                .whereEqualTo("matricula", binding.matriculaCoche.text.toString())
                .get().addOnSuccessListener {
                    it.forEach{
                        binding.marcaCoche.setText(it.get("marca") as String?)
                        binding.modeloCoche.setText(it.get("modelo") as String?)
                        binding.colorCoche.setText(it.get("color") as String?)
                    }
                }
        }

        /*
        binding.botonEliminarCoche.setOnClickListener {
            db.collection("coches")
                .get()
                .addOnSuccessListener {
                    it.forEach{
                        it.reference.delete()
                    }
                }
        }

         */
    }





}