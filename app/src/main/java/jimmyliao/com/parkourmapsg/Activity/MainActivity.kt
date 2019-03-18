package jimmyliao.com.parkourmapsg.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import jimmyliao.com.parkourmapsg.Adapter.SpotAdapter
import jimmyliao.com.parkourmapsg.Constants
import jimmyliao.com.parkourmapsg.LocalDB.SpotDAO
import jimmyliao.com.parkourmapsg.Module.Spot
import jimmyliao.com.parkourmapsg.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val testSpots = mutableListOf<Spot>()
    private lateinit var spotDAO: SpotDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllSpot()
        setAdapter()
    }

    private fun getAllSpot() {
        spotDAO = SpotDAO(applicationContext)
        val areas = Constants.areas
        for(area in areas){
            getSpot(area)
        }
    }

    private fun getSpot(areaName: String) {
        if (spotDAO.count(areaName) == 0) {
            val db = FirebaseFirestore.getInstance()
            db.collection(areaName).orderBy("Name",Query.Direction.ASCENDING).get().addOnSuccessListener { documents ->
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
                }
                testSpots.addAll(spotDAO.getAll(areaName))
                updateRecyclerView()
            }
        }else{
            testSpots.addAll(spotDAO.getAll(areaName))
            updateRecyclerView()
        }
    }

    private fun setAdapter() {
        recycler_main.adapter = SpotAdapter(this, testSpots)
        recycler_main.layoutManager = LinearLayoutManager(this)
    }

    private fun updateRecyclerView() {
        recycler_main.adapter?.notifyDataSetChanged()
    }
}
