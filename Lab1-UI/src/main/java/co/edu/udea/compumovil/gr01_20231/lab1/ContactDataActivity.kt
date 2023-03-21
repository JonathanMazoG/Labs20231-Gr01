package co.edu.udea.compumovil.gr01_20231.lab1

import android.os.Bundle
import android.util.Log
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import co.edu.udea.compumovil.gr01_20231.lab1.CountryApiService

class ContactDataActivity : AppCompatActivity() {
    data class Pais(val name: String, val alpha2Code: String)
    private lateinit var telefonoEditText: EditText
    private lateinit var direccionEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var paisAutoCompleteTextView: AutoCompleteTextView
    private lateinit var estadoAutoCompleteTextView: AutoCompleteTextView
    private lateinit var ciudadAutoCompleteTextView: AutoCompleteTextView
    private lateinit var siguiente1Button: Button

    private var telefono: String = ""
    private var direccion: String = ""
    private var correo: String = ""
    private var pais: String = ""
    private var estado: String = ""
    private var ciudad: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_data_activity)

        telefonoEditText = findViewById(R.id.telefono_edittext)
        direccionEditText = findViewById(R.id.direccion_edittext)
        correoEditText = findViewById(R.id.correo_edittext)
        paisAutoCompleteTextView = findViewById(R.id.pais_spinner)
        estadoAutoCompleteTextView = findViewById(R.id.estado_spinner)
        ciudadAutoCompleteTextView = findViewById(R.id.ciudad_spinner)
        siguiente1Button = findViewById(R.id.siguiente1_button)

        paisAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    estadoAutoCompleteTextView.isEnabled = false
                } else {
                    estadoAutoCompleteTextView.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        estadoAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    ciudadAutoCompleteTextView.isEnabled = false
                } else {
                    ciudadAutoCompleteTextView.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        paisAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            pais = parent.getItemAtPosition(position).toString()

            CountryApiService.fetchStateByCountry(pais) { states, error ->
                runOnUiThread {
                    if (error != null) {
                        Log.e("Ha ocurrido un error ", "el error consiste en", error)
                    } else {
                        Log.d("states:------------------------------------------- ", states.toString())
                        val adapter = ArrayAdapter(this@ContactDataActivity, android.R.layout.simple_dropdown_item_1line, states!!)
                        estadoAutoCompleteTextView.setAdapter(adapter)
                        estadoAutoCompleteTextView.threshold = 1
                    }
                }
            }
        }

        estadoAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            estado = parent.getItemAtPosition(position).toString()

            CountryApiService.fetchCitiesByStates(estado) { cities, error ->
                runOnUiThread {
                    if (error != null) {
                        Log.e("Ha ocurrido un error ", "el error consiste en", error)
                    } else {
                        Log.d("Cities:------------------------------------------- ", cities.toString())
                        val adapter = ArrayAdapter(this@ContactDataActivity, android.R.layout.simple_dropdown_item_1line, cities!!)
                        ciudadAutoCompleteTextView.setAdapter(adapter)
                        ciudadAutoCompleteTextView.threshold = 1
                    }
                }
            }
        }

        // Llamar a la función fetchCountries al iniciar la actividad
        CountryApiService.fetchCountries() { countries, error ->
            runOnUiThread {
                if (error != null) {
                    Log.e("Ha ocurrido un error ", "el error consiste en", error)
                } else {
                    Log.d("Countries:------------------------------------------- ", countries.toString())
                    val adapter = ArrayAdapter(this@ContactDataActivity, android.R.layout.simple_dropdown_item_1line, countries!!)
                    paisAutoCompleteTextView.setAdapter(adapter)
                    paisAutoCompleteTextView.threshold = 1
                }
            }
        }


        siguiente1Button.setOnClickListener {
            telefono = telefonoEditText.text.toString()
            direccion = direccionEditText.text.toString()
            correo = correoEditText.text.toString()
            pais = paisAutoCompleteTextView.text.toString()
            ciudad = ciudadAutoCompleteTextView.text.toString()

            if (telefono.isEmpty()) {
                telefonoEditText.error = getString(R.string.errortel)
                return@setOnClickListener
            }
            if (correo.isEmpty()) {
                correoEditText.error =  getString(R.string.errorcorreo)
                return@setOnClickListener
            }
            if (pais.isEmpty()) {
                paisAutoCompleteTextView.error =  getString(R.string.errorpais)
                return@setOnClickListener
            }
            val datos1 = listOf(telefono, direccion, correo, pais, ciudad)

            Log.d("TAG", "------------------------------")
            Log.d("TAG", "Información de contacto:")
            Log.d("TAG", String.format("%-20s%s", "Teléfono:", telefono))

            if(!direccion.isNullOrEmpty()) {
                Log.d("TAG", String.format("%-20s%s", "Dirección:", direccion))
            }
            Log.d("TAG", String.format("%-20s%s", "Email:", correo))
            Log.d("TAG", String.format("%-20s%s", "País:", pais))

            if(!ciudad.isNullOrEmpty()) {
                Log.d("TAG", String.format("%-20s%s", "Ciudad:", ciudad))
            }

            val intent = Intent(this, PersonalDataActivity::class.java)
            startActivity(intent)
        }
    }

}