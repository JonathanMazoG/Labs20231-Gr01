package co.edu.udea.compumovil.gr01_20231.lab1

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

data class Country(val country_name: String)
data class States(val state_name: String)
data class Cities(val city_name: String)

class CountryApiService {
    companion object {
        private const val BASE_URL = "https://www.universal-tutorial.com/api/"
        private const val API_TOKEN = "yke87wus4OSma5pxA-eH9TWTLlWG7qI136el608R7sfSWSS2WGjHNtS2yt7Z81X5hLk"
        private const val USER_EMAIL = "andres.lema1@udea.edu.co"

        fun fetchCountries(callback: (List<String>?, Exception?) -> Unit) {
            FetchCountriesAsyncTask(callback).execute()
        }

        fun fetchCitiesByStates(state: String, callback: (List<String>?, Exception?) -> Unit) {
            FetchCitiesAsyncTask(state, callback).execute()
        }

        private class FetchCitiesAsyncTask(private val state: String, private val callback: (List<String>?, Exception?) -> Unit) : AsyncTask<Unit, Unit, Pair<List<String>?, Exception?>>() {
            override fun doInBackground(vararg params: Unit?): Pair<List<String>?, Exception?> {
                val client = OkHttpClient()
                val token = getAuthToken()

                if (token == null) {
                    return Pair(null, Exception("Unable to get authentication token"))
                }

                val request = Request.Builder()
                    .url("${BASE_URL}cities/$state")
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                return try {
                    val response = client.newCall(request).execute()
                    val json = response.body?.string()
                    if (json != null) {
                        val gson = Gson()
                        val cities = gson.fromJson(json, Array<Cities>::class.java)
                        val citiesList = cities.map { it.city_name }
                        Pair(citiesList, null)
                    } else {
                        Pair(null, Exception("Empty response"))
                    }
                } catch (e: IOException) {
                    Pair(null, e)
                }
            }

            override fun onPostExecute(result: Pair<List<String>?, Exception?>) {
                callback(result.first, result.second)
            }
        }

        fun fetchStateByCountry(country: String, callback: (List<String>?, Exception?) -> Unit) {
            FetchStatesAsyncTask(country, callback).execute()
        }

        private class FetchStatesAsyncTask(private val country: String, private val callback: (List<String>?, Exception?) -> Unit) : AsyncTask<Unit, Unit, Pair<List<String>?, Exception?>>() {
            override fun doInBackground(vararg params: Unit?): Pair<List<String>?, Exception?> {
                val client = OkHttpClient()
                val token = getAuthToken()

                if (token == null) {
                    return Pair(null, Exception("Unable to get authentication token"))
                }

                val request = Request.Builder()
                    .url("${BASE_URL}states/$country")
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                return try {
                    val response = client.newCall(request).execute()
                    val json = response.body?.string()
                    if (json != null) {
                        val gson = Gson()
                        val states = gson.fromJson(json, Array<States>::class.java)
                        val statesList = states.map { it.state_name }
                        Pair(statesList, null)
                    } else {
                        Pair(null, Exception("Empty response"))
                    }
                } catch (e: IOException) {
                    Pair(null, e)
                }
            }

            override fun onPostExecute(result: Pair<List<String>?, Exception?>) {
                callback(result.first, result.second)
            }
        }

        private class FetchCountriesAsyncTask(private val callback: (List<String>?, Exception?) -> Unit) : AsyncTask<Unit, Unit, Pair<List<String>?, Exception?>>() {
            override fun doInBackground(vararg params: Unit?): Pair<List<String>?, Exception?> {
                val client = OkHttpClient()
                val token = getAuthToken()

                if (token == null) {
                    return Pair(null, Exception("Unable to get authentication token"))
                }

                val request = Request.Builder()
                    .url("${BASE_URL}countries/")
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                return try {
                    val response = client.newCall(request).execute()
                    val json = response.body?.string()
                    if (json != null) {
                        val gson = Gson()
                        val countries = gson.fromJson(json, Array<Country>::class.java)
                        val countryList = countries.map { it.country_name }
                        Pair(countryList, null)
                    } else {
                        Pair(null, Exception("Empty response"))
                    }
                } catch (e: IOException) {
                    Pair(null, e)
                }
            }

            override fun onPostExecute(result: Pair<List<String>?, Exception?>) {
                callback(result.first, result.second)
            }
        }

        private fun getAuthToken(): String? {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("${BASE_URL}getaccesstoken/")
                .addHeader("Accept", "application/json")
                .addHeader("api-token", API_TOKEN)
                .addHeader("user-email", USER_EMAIL)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e("CountryApiService", "Failed to get authentication token")
                    return null
                }

                val json = response.body?.string()
                val tokenResponse = Gson().fromJson(json, TokenResponse::class.java)
                return tokenResponse.auth_token
            }
        }


        private data class TokenResponse(val auth_token: String)
    }
}
