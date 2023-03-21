package co.edu.udea.compumovil.gr01_20231.lab1

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity

import java.util.*

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var nombresEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var sexoRadioGroup: RadioGroup
    private lateinit var sexoMRadioButton: RadioButton
    private lateinit var sexoFRadioButton: RadioButton
    private lateinit var fechaNacimientoEditText: EditText
    private lateinit var gradoEscolaridadSpinner: Spinner
    private lateinit var siguienteButton: Button

    private var nombres: String = ""
    private var apellidos: String = ""
    private var sexo: String = ""
    private var fechaNacimiento: String = ""
    private var gradoEscolaridad: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        nombresEditText = findViewById(R.id.nombres_edittext)
        apellidosEditText = findViewById(R.id.apellidos_edittext)
        sexoRadioGroup = findViewById(R.id.sexo_radiogroup)
        sexoMRadioButton = findViewById(R.id.sexo_m_radiobutton)
        sexoFRadioButton = findViewById(R.id.sexo_f_radiobutton)
        fechaNacimientoEditText = findViewById(R.id.fechanacimiento_edittext)
        gradoEscolaridadSpinner = findViewById(R.id.gradoescolaridad_spinner)
        siguienteButton = findViewById(R.id.siguiente_button)

        fechaNacimientoEditText.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, y, m, d ->
                    val dateStr = String.format(Locale.getDefault(), "%02d/%02d/%04d", d, m + 1, y)
                    fechaNacimientoEditText.setText(dateStr)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        siguienteButton.setOnClickListener {
            nombres = nombresEditText.text.toString()
            apellidos = apellidosEditText.text.toString()
            when (sexoRadioGroup.checkedRadioButtonId) {
                R.id.sexo_m_radiobutton -> sexo = getString(R.string.masculino)
                R.id.sexo_f_radiobutton -> sexo = getString(R.string.femenino)
            }
            if (nombres.isEmpty()) {
                nombresEditText.error = getString(R.string.errornombres)
                return@setOnClickListener
            }
            if (apellidos.isEmpty()) {
                apellidosEditText.error =  getString(R.string.errorapellidos)
                return@setOnClickListener
            }
            fechaNacimiento = fechaNacimientoEditText.text.toString()
            gradoEscolaridad = gradoEscolaridadSpinner.selectedItem.toString()
            if (fechaNacimiento.isEmpty()) {
                fechaNacimientoEditText.error =  getString(R.string.errorfnacimiento)
                return@setOnClickListener
            }


            val datos = listOf(nombres, apellidos, sexo, fechaNacimiento, gradoEscolaridad)

            Log.d("TAG", "------------------------------")
            Log.d("TAG", getString(R.string.informacionpersonal))
            Log.d("TAG", String.format("%-20s%s %s", getString(R.string.nombre), nombres, apellidos))
            Log.d("TAG", String.format("%-20s%s", getString(R.string.sexo), sexo))

            Log.d("TAG", String.format("%-20s%s", getString(R.string.nacio), fechaNacimiento))
            if (!gradoEscolaridad.isNullOrEmpty()) {
                if (gradoEscolaridad!="None"){
                Log.d("TAG", String.format( "%-20s%s", getString(R.string.grado_escolaridad), gradoEscolaridad))}
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}