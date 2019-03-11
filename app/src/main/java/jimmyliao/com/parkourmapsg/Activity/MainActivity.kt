package jimmyliao.com.parkourmapsg.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import jimmyliao.com.parkourmapsg.Adapter.SpotAdapter
import jimmyliao.com.parkourmapsg.Module.Spot
import jimmyliao.com.parkourmapsg.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var testSpots = mutableListOf<Spot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        createSpot()
        getSpot()
        setAdapter()
    }

    private fun createSpot() {
        for (n in 1..50) {
            testSpots.add(Spot("spot $n", "test description $n", getDrawable(R.drawable.pksgfinal_colour_pks_white)))
        }
    }

    private fun getSpot() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Central").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val data = doc.data
                testSpots.add(Spot(data.get("Name").toString(),data.get("Description").toString(),getDrawable(R.drawable.pksgfinal_colour_pks_white)))
            }
            updateRecyclerView()
        }
    }

    private fun setAdapter() {
        recycler_main.adapter = SpotAdapter(this, testSpots)
        recycler_main.layoutManager = LinearLayoutManager(this)
    }

    private fun updateRecyclerView(){
        recycler_main.adapter?.notifyDataSetChanged()
    }
}
