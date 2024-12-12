package com.unistra.m2info.composemymeal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.unistra.m2info.composemymeal.API.ApiService
import com.unistra.m2info.composemymeal.API.RetrofitClient
import com.unistra.m2info.composemymeal.ui.theme.ComposeMyMealTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMyMealTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }

        val testApi = RetrofitClient.getInstance().create(ApiService::class.java)
        GlobalScope.launch {
            val result = testApi.getMealByID()
            if(result != null){
                //val jsonT = Gson().toJson(result)
                Log.d("Resultat : ", result.request().body().toString())
            }
        }
    }
}