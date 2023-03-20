package co.edu.udea.compumovil.gr01_20231.lab1

import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
data class Country(val name: Name)

data class Name(val common: String, val official: String)

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
                if (json != null) {
                    val gson = Gson()
                    val countries = gson.fromJson(json, Array<Country>::class.java)
                    val countryNames = countries.map { it.name.common }
                    val sortedCountries = countryNames.sorted()
                    val countriesByLetter = sortedCountries.groupBy { it.first().toUpperCase() }
                    val countryList = countriesByLetter.flatMap { (letter, countries) ->
                        listOf(letter.toString()) + countries.sorted()
                    }
                    callback(countryList, null)
                } else {
                    callback(null, Exception("Empty response"))
                }
            }
        })
    }
}
