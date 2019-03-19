package jimmyliao.com.parkourmapsg.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import jimmyliao.com.parkourmapsg.Constants
import jimmyliao.com.parkourmapsg.LocalDB.SpotDAO
import jimmyliao.com.parkourmapsg.Module.Spot
import jimmyliao.com.parkourmapsg.R
import kotlinx.android.synthetic.main.activity_intro.*
import java.util.*
import kotlin.concurrent.schedule

class IntroActivity : AppCompatActivity() {
    private var dataMax = 0
    private var dataProgress = 0
    private var centralReady = false
    private var eastReady = false
    private var northReady = false
    private var northeastReady = false
    private var westReady = false
    private val testSpots = mutableListOf<Spot>()
    private lateinit var spotDAO: SpotDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        getAllSpot()
    }

    private fun getAllSpot() {
        spotDAO = SpotDAO(applicationContext)
        getCentral()
        getEast()
        getNorth()
        getNorthEast()
        getWest()
    }

    private fun getCentral() {
        if (spotDAO.count(Constants.area_central) == 0) {
            progress_horizontal.visibility = View.VISIBLE
            tv_loading_data.visibility = View.VISIBLE
            getSpot(Constants.area_central) {
                centralReady = true
                checkToNextActivity()
            }
        } else {
            centralReady = true
            checkToNextActivity()
        }
    }

    private fun getEast() {
        if (spotDAO.count(Constants.area_east) == 0) {
            progress_horizontal.visibility = View.VISIBLE
            tv_loading_data.visibility = View.VISIBLE
            getSpot(Constants.area_east) {
                eastReady = true
                checkToNextActivity()
            }
        } else {
            eastReady = true
            checkToNextActivity()
        }
    }

    private fun getNorth() {
        if (spotDAO.count(Constants.area_north) == 0) {
            progress_horizontal.visibility = View.VISIBLE
            tv_loading_data.visibility = View.VISIBLE
            getSpot(Constants.area_north) {
                northReady = true
                checkToNextActivity()
            }
        } else {
            northReady = true
            checkToNextActivity()
        }
    }

    private fun getNorthEast() {
        if (spotDAO.count(Constants.area_northeast) == 0) {
            progress_horizontal.visibility = View.VISIBLE
            tv_loading_data.visibility = View.VISIBLE
            getSpot(Constants.area_northeast) {
                northeastReady = true
                checkToNextActivity()
            }
        } else {
            northeastReady = true
            checkToNextActivity()
        }
    }

    private fun getWest() {
        if (spotDAO.count(Constants.area_west) == 0) {
            progress_horizontal.visibility = View.VISIBLE
            tv_loading_data.visibility = View.VISIBLE
            getSpot(Constants.area_west) {
                westReady = true
                checkToNextActivity()
            }
        } else {
            westReady = true
            checkToNextActivity()
        }
    }

    private fun getSpot(areaName: String, callback: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection(areaName).orderBy("Name", Query.Direction.ASCENDING).get().addOnSuccessListener { documents ->
            dataMax += documents.size()
            progress_horizontal.max = dataMax
            for (doc in documents) {
                spotDAO.insert(
                    areaName,
                    Spot(
                        0,
                        doc.getString("Name"),
                        doc.getString("Description"),
                        doc.getDouble("Latitude"),
                        doc.getDouble("Longitude")
                    )
                )
                dataProgress++
                progress_horizontal.progress = dataProgress
            }
            testSpots.addAll(spotDAO.getAll(areaName))
            callback()
        }
    }

    private fun checkToNextActivity() {
        if (centralReady && eastReady && northReady && northeastReady && westReady) {
            val timer = Timer("IntroDelay", true)
            timer.schedule(3000) {
                startActivity(Intent(this@IntroActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}
