package com.gbeho.weatherapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbeho.weatherapplication.model.CityBean
import com.gbeho.weatherapplication.outilsAndroid.CityAdapter
import com.gbeho.weatherapplication.utils.WSUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val KEY_INTENT_PLACE = "place"
    private val KEY_INTENT_POSITION_LAT = "lat"
    private val KEY_INTENT_POSITION_LON = "long"

    val data = ArrayList<CityBean>()

    val adapter = CityAdapter(data)

    var thread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvZip.adapter = adapter
        rvZip.layoutManager = GridLayoutManager(this, 1)
    }

    //Callback clic bouton Search Place
    fun onBtSearchPlaceClic(view: View) {
        //Envoyer le contenu de l'edit text dans DisplayWeatherActivity
        val intent = Intent(this, DisplayWeatherActivity::class.java)
        intent.putExtra(KEY_INTENT_PLACE, etPlaceName.text.toString())
        this.startActivity(intent)
    }

    //Callback clic bouton Search Zip Code
    fun onBtSearchZipClic(view: View) {
        val cp = etZipCode.text.toString()
        // Créer un thread qui lance la requête vers le webservice et l'afficher dans le text view
        if (thread == null || thread!!.state == Thread.State.TERMINATED) {
            thread = thread {
                try {
                    data.clear()
                    data.addAll(WSUtils.getCities(cp))
                    runOnUiThread { adapter.notifyDataSetChanged() }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread { Toast.makeText(this, e.message, Toast.LENGTH_LONG).show() }
                }
            }
        }
    }

    //Callback clic bouton Search Position
    fun onBtSearchPositionClic(view: View) {
        //Envoyer le contenu de l'edit text dans DisplayWeatherActivity
        val intent = Intent(this, DisplayWeatherActivity::class.java)
        intent.putExtra(KEY_INTENT_POSITION_LAT, etLat.text.toString())
        intent.putExtra(KEY_INTENT_POSITION_LON, etLong.text.toString())
        this.startActivity(intent)
    }


    //Callback Radio Button Zip Code checked
    fun onRbZipCodeClic(view: View) {
        if (rbZipCode.isChecked){
            findViewById<LinearLayout>(R.id.llZipCode).visibility = View.VISIBLE
            findViewById<RecyclerView>(R.id.rvZip).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.llPlace).visibility = View.GONE
            findViewById<LinearLayout>(R.id.llPosition).visibility = View.GONE
        }
    }

    //Callback Radio Button Place checked
    fun onRbPlaceClic(view: View) {
        if (rbPlace.isChecked){
            findViewById<LinearLayout>(R.id.llPlace).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.llZipCode).visibility = View.GONE
            findViewById<RecyclerView>(R.id.rvZip).visibility = View.GONE
            findViewById<LinearLayout>(R.id.llPosition).visibility = View.GONE

            data.clear()
        }

    }

    //Callback Radio Button Position checked
    fun onRbPositionClic(view: View) {
        if (rbPosition.isChecked){
            findViewById<LinearLayout>(R.id.llPosition).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.llZipCode).visibility = View.GONE
            findViewById<RecyclerView>(R.id.rvZip).visibility = View.GONE
            findViewById<LinearLayout>(R.id.llPlace).visibility = View.GONE

            data.clear()
        }

    }


}