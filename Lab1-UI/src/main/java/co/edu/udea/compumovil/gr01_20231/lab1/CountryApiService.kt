package co.edu.udea.compumovil.gr01_20231.lab1

import okhttp3.*
import java.io.IOException
import kotlin.math.log

class CountryApiService {

    companion object {
        private const val BASE_URL = "https://restcountries.com/v3.1/"
    }

    fun fetchCountries(region: String, callback: (List<String>?, Exception?) -> Unit) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("$BASE_URL/region/$region")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val countries = parseCountries(json)
                callback(countries, null)

            }
        })
    }

    private fun parseCountries(json: String?): List<String>? {
        // Aquí deberías implementar la lógica para parsear la respuesta JSON
        // y extraer los nombres de los países. Depende de la estructura de la
        // respuesta de la API.
        return null
    }
}


