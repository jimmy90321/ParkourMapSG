package jimmyliao.com.parkourmapsg.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jimmyliao.com.parkourmapsg.LocalDB.SpotDAO
import jimmyliao.com.parkourmapsg.R
import kotlinx.android.synthetic.main.activity_spot_detail.*

class SpotDetailActivity : AppCompatActivity() {
    private var spotId = 0L
    private lateinit var spotArea: String

    companion object {
        const val SPOT_AREA = "area"
        const val SPOT_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_detail)
        spotArea = intent.getStringExtra(SPOT_AREA)
        spotId = intent.getLongExtra(SPOT_ID,0L)

        setData()
    }

    private fun setData() {
        val spotDAO = SpotDAO(applicationContext)
        val spot = spotDAO.get(spotArea,spotId)
        tv_spot_detail_name.text = spot?.name
        tv_spot_detail_description.text = spot?.description
    }
}
