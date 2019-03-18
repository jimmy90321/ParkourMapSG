package jimmyliao.com.parkourmapsg.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import jimmyliao.com.parkourmapsg.Fragment.MainFragment
import jimmyliao.com.parkourmapsg.Fragment.SpotFragment

class MainPagerAdapter(manager: FragmentManager, private var fragmentNumber: Int) : FragmentStatePagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> SpotFragment()
            else -> MainFragment()
        }
    }

    override fun getCount(): Int = fragmentNumber

}