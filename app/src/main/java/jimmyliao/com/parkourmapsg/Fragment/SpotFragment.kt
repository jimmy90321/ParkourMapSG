package jimmyliao.com.parkourmapsg.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import jimmyliao.com.parkourmapsg.Adapter.SpotAdapter
import jimmyliao.com.parkourmapsg.Constants
import jimmyliao.com.parkourmapsg.LocalDB.SpotDAO
import jimmyliao.com.parkourmapsg.Module.Spot

import jimmyliao.com.parkourmapsg.R
import kotlinx.android.synthetic.main.fragment_spot.*

class SpotFragment : Fragment(), CompoundButton.OnCheckedChangeListener {
    private val testSpots = mutableListOf<Spot>()
    private lateinit var spotDAO: SpotDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_spot, container, false)
    }

    override fun onResume() {
        super.onResume()
        spotDAO = SpotDAO(this.requireContext())
        initView()
        initData()
    }

    private fun initView() {
        rb_area_all.setOnCheckedChangeListener(this)
        rb_area_central.setOnCheckedChangeListener(this)
        rb_area_east.setOnCheckedChangeListener(this)
        rb_area_north.setOnCheckedChangeListener(this)
        rb_area_northeast.setOnCheckedChangeListener(this)
        rb_area_west.setOnCheckedChangeListener(this)
    }

    private fun initData() {
        recycler_spot.adapter = SpotAdapter(this.requireContext(), testSpots)
        recycler_spot.layoutManager = LinearLayoutManager(this.requireContext())
    }


    override fun onCheckedChanged(btn: CompoundButton?, isChecked: Boolean) {
        when (btn) {
            rb_area_central -> {
                rg_area2.clearCheck()
                setData(Constants.area_central)
            }
            rb_area_east -> {
                rg_area2.clearCheck()
                setData(Constants.area_east)
            }
            rb_area_north -> {
                rg_area2.clearCheck()
                setData(Constants.area_north)
            }
            rb_area_northeast -> {
                rg_area1.clearCheck()
                setData(Constants.area_northeast)
            }
            rb_area_west -> {
                rg_area1.clearCheck()
                setData(Constants.area_west)
            }
            else -> {
                rg_area1.clearCheck()
                setData(
                    Constants.area_central,
                    Constants.area_east,
                    Constants.area_north,
                    Constants.area_northeast,
                    Constants.area_west
                )
            }
        }
    }

    private fun setData(vararg area: String) {
        testSpots.clear()
        for (areaName in area) {
            testSpots.addAll(spotDAO.getAll(areaName))
        }
        recycler_spot.adapter?.notifyDataSetChanged()
    }
}
