package co.edu.udea.compumovil.gr01_20231.lab1

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.view.inputmethod.EditorInfo


import androidx.appcompat.app.AppCompatActivity

import java.util.*

class ContactDataActivity : AppCompatActivity() {
    data class Pais(val name: String, val alpha2Code: String)
    private lateinit var telefonoEditText: EditText
    private lateinit var direccionEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var paisSpinner: Spinner
    private lateinit var ciudadSpinner: Spinner
    private lateinit var siguiente1Button: Button

    private var telefono: String = ""
    private var direccion: String = ""
    private var correo: String = ""
    private var pais: String = ""
    private var ciudad: String = ""

    private val countryApiService = CountryApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_data_activity)

        telefonoEditText = findViewById(R.id.telefono_edittext)
        direccionEditText = findViewById(R.id.direccion_edittext)
        correoEditText = findViewById(R.id.correo_edittext)
        paisSpinner= findViewById(R.id.pais_spinner)
        ciudadSpinner = findViewById(R.id.ciudad_spinner)
        siguiente1Button = findViewById(R.id.siguiente1_button)




        siguiente1Button.setOnClickListener {
            telefono = telefonoEditText.text.toString()
            direccion = direccionEditText.text.toString()
            correo = correoEditText.text.toString()
            //pais = paisSpinner.selectedItem.toString()
           // ciudad = ciudadSpinner.selectedItem.toString()
            Log.d("ContactDataActivity", "eNTREEEEEEEEEEEEE AL SIGUIENTE")
            if (telefono.isEmpty()) {
                telefonoEditText.error = getString(R.string.errortel)
                return@setOnClickListener
            }
            if (correo.isEmpty()) {
                correoEditText.error =  getString(R.string.errorcorreo)
                return@setOnClickListener
            }
            val datos1 = listOf(telefono, direccion, correo, pais, ciudad)
            Log.d("ContactDataActivity", datos1.joinToString(", "))

            countryApiService.fetchCountries("Americas") { countries, error ->
                Log.d("Countries:------------------------------------------- ",
                    countries.toString()
                )
                if (error != null) {
                    Log.e("Countries:------------------------------------------- ", "vvv")
                } else {
                    Log.d("Countries:------------------------------------------- ", countries.toString())

                }
            }
        }



    }
}