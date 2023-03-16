package co.edu.udea.compumovil.gr01_20231.lab1

import android.app.DatePickerDialog
import android.view.View
import android.widget.EditText
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        // Obtener referencia al EditText de fecha de nacimiento
        val fechaNacimientoEditText = findViewById<EditText>(R.id.fechanacimiento_edittext)

// Crear objeto DatePickerDialog.OnDateSetListener para actualizar el texto del EditText
        val fechaNacimientoListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Actualizar texto del EditText con la fecha seleccionada
            val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
            fechaNacimientoEditText.setText(fechaSeleccionada)
        }

// Crear objeto DatePickerDialog y configurarlo para mostrar un calendario
        val calendar = Calendar.getInstance()
        val anioActual = calendar.get(Calendar.YEAR)
        val mesActual = calendar.get(Calendar.MONTH)
        val diaActual = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, fechaNacimientoListener, anioActual, mesActual, diaActual)
        dialog.datePicker.maxDate = Date().time // Limitar la fecha máxima al día de hoy

// Asignar evento onClick al EditText para mostrar el DatePickerDialog
        fechaNacimientoEditText.setOnClickListener {
            dialog.show()
        }

    }
}