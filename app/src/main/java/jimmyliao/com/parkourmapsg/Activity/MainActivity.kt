package jimmyliao.com.parkourmapsg.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import jimmyliao.com.parkourmapsg.Adapter.SpotAdapter
import jimmyliao.com.parkourmapsg.LocalDB.SpotDAO
import jimmyliao.com.parkourmapsg.Module.Spot
import jimmyliao.com.parkourmapsg.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var testSpots = mutableListOf<Spot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSpot()
        setAdapter()
    }

    private fun getSpot() {
        //TODO: get all area spots
        val spotDAO = SpotDAO(applicationContext)
        if (spotDAO.count("Central") == 0) {
            val db = FirebaseFirestore.getInstance()
            db.collection("Central").get().addOnSuccessListener { documents ->
                for (doc in documents) {
                    spotDAO.insert(
                        "Central",
                        Spot(
                            0,
                            doc.getString("Name"),
                            doc.getString("Description"),
                            doc.getDouble("Latitude"),
                            doc.getDouble("Longitude")
                        )
                    )
                }
                updateRecyclerView()
            }
        }
        testSpots = spotDAO.getAll("Central")
    }

    private fun setAdapter() {
        recycler_main.adapter = SpotAdapter(this, testSpots)
        recycler_main.layoutManager = LinearLayoutManager(this)
    }

    private fun updateRecyclerView() {
        recycler_main.adapter?.notifyDataSetChanged()
    }
}
