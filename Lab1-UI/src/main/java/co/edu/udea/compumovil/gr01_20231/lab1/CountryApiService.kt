package co.edu.udea.compumovil.gr01_20231.lab1

import okhttp3.*
import java.io.IOException
import kotlin.math.log
import org.json.JSONObject
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
        json?.let {
            val jsonObject = JSONObject(it)
            val countriesArray = jsonObject.getJSONArray("countries")

            val countriesList = mutableListOf<String>()
            for (i in 0 until countriesArray.length()) {
                val countryObject = countriesArray.getJSONObject(i)
                val countryName = countryObject.getJSONObject("name").getString("common")
                countriesList.add(countryName)
            }
            return countriesList
        }
        return null
    }
}


