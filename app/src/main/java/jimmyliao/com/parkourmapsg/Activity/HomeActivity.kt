package jimmyliao.com.parkourmapsg.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import jimmyliao.com.parkourmapsg.Adapter.MainPagerAdapter
import jimmyliao.com.parkourmapsg.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupViewPager()
    }

    private fun initViews() {
//        tabs = tab
//        viewPager = mainPager
        mainPager.adapter = MainPagerAdapter(supportFragmentManager,2)
        tab.setupWithViewPager(mainPager)

        val one = tab.getTabAt(0)
        val two = tab.getTabAt(1)

        one?.text = "Home"
        two?.text = "Spot"
    }

    private fun setupViewPager() {

    }
}
