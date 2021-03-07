package com.gbeho.weatherapplication.utils

import okhttp3.OkHttpClient
import okhttp3.Request


class OkhttpUtils {

    companion object {
        val client = OkHttpClient()

        fun sendGetOkHttpRequest(url: String): String {
            println("url : $url")
            //Création de la requete
            val request = Request.Builder().url(url).build()
            //Execution de la requête
            val response = client.newCall(request).execute()
            //Analyse du code retour
            return if (response.code() !in 200..299) {
                throw Exception("Réponse du serveur incorrect : ${response.code()}")
            }
            else {
                //Résultat de la requete.
                //ATTENTION .string ne peut être appelée qu’une seule fois.
                response.body()?.string() ?: ""
            }
        }
    }

}