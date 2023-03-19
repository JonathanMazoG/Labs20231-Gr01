package co.edu.udea.compumovil.gr01_20231.lab1

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import co.edu.udea.compumovil.gr01_20231.lab1.databinding.ContactDataActivityBinding
import java.util.*

class ContactDataActivity : AppCompatActivity() {

    private lateinit var telefonoEditText: EditText
    private lateinit var direccionEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var paisEditText: EditText
    private lateinit var ciudadEditText: EditText
    private lateinit var siguiente1Button: Button

    private var telefono: String = ""
    private var direccion: String = ""
    private var correo: String = ""
    private var pais: String = ""
    private var ciudad: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        telefonoEditText = findViewById(R.id.nombres_edittext)
        direccionEditText = findViewById(R.id.apellidos_edittext)
        correoEditText = findViewById(R.id.correo_edittext)
        paisEditText= findViewById(R.id.pais_edittext)
        ciudadEditText = findViewById(R.id.ciudad_edittext)
        siguiente1Button = findViewById(R.id.siguiente1_button)



        siguiente1Button.setOnClickListener {
            telefono = telefonoEditText.text.toString()
            direccion = direccionEditText.text.toString()
            correo = correoEditText.text.toString()
            pais = paisEditText.text.toString()
            ciudad = ciudadEditText.text.toString()

            val datos1 = listOf(telefono, direccion, correo, pais, ciudad)
            Log.d("PersonalDataActivity", datos1.joinToString(", "))
        }
    }
}