package com.paysky.momogrow.views.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paysky.momogrow.R
import com.paysky.momogrow.utilis.Constants
import com.paysky.momogrow.utilis.PreferenceProcessor
import com.paysky.momogrow.views.products.AddProductActivity
import com.paysky.momogrow.views.products.ProductsFragment
import com.paysky.momogrow.views.more.MoreFragment
import com.paysky.momogrow.views.notifications.NotificationsActivity
import com.paysky.momogrow.views.orders.OrdersFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, HomeFragment()).commit()
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragments_container, HomeFragmentJava()).commit()
                    fabAdd.visibility = View.GONE
                    ivCalculate.visibility = View.VISIBLE
                    container.visibility = View.VISIBLE
                    fragments_more_container.visibility = View.GONE
                    showHideLine1()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.orders -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, OrdersFragment()).commit()
                    fabAdd.visibility = View.GONE
                    ivCalculate.visibility = View.GONE
                    container.visibility = View.VISIBLE
                    fragments_more_container.visibility = View.GONE
                    showHideLine2()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.catalog -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_container, ProductsFragment()).commit()
                    fabAdd.visibility = View.VISIBLE
                    ivCalculate.visibility = View.GONE
                    container.visibility = View.VISIBLE
                    fragments_more_container.visibility = View.GONE
                    showHideLine3()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments_more_container, MoreFragment()).commit()
                    fabAdd.visibility = View.GONE
                    ivCalculate.visibility = View.GONE
                    container.visibility = View.GONE
                    fragments_more_container.visibility = View.VISIBLE
                    showHideLine4()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun showHideLine1() {
        line1.setBackgroundColor(getColor(R.color.purple_500))
        line2.setBackgroundColor(Color.TRANSPARENT)
        line3.setBackgroundColor(Color.TRANSPARENT)
        line4.setBackgroundColor(Color.TRANSPARENT)

    }

    private fun showHideLine2() {
        line2.setBackgroundColor(getColor(R.color.purple_500))
        line1.setBackgroundColor(Color.TRANSPARENT)
        line3.setBackgroundColor(Color.TRANSPARENT)
        line4.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun showHideLine3() {
        line3.setBackgroundColor(getColor(R.color.purple_500))
        line2.setBackgroundColor(Color.TRANSPARENT)
        line1.setBackgroundColor(Color.TRANSPARENT)
        line4.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun showHideLine4() {
        line4.setBackgroundColor(getColor(R.color.purple_500))
        line2.setBackgroundColor(Color.TRANSPARENT)
        line3.setBackgroundColor(Color.TRANSPARENT)
        line1.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        PreferenceProcessor.setStr(Constants.Companion.Preference.AUTH_TOKEN, "1637134988Z1Gw0oZPomBUzs4rCzM1AFQZH88hgQsMoHABNc3fz0gGNK6g")

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        var previousScrollY = 0
//        container.viewTreeObserver.addOnScrollChangedListener { // previousScrollY this variable is define in your Activity or Fragment
//
//            if (container.scrollY - previousScrollY > 20 && ivCalculate.visibility == View.VISIBLE) {
//                ivCalculate.visibility = View.GONE
//
//            } else if (previousScrollY - container.scrollY > 20 && ivCalculate.visibility != View.VISIBLE) {
//                if (bottom_navigation.selectedItemId == R.id.dashboard)
//                    ivCalculate.visibility = View.VISIBLE
//            }
//            if (container.scrollY - previousScrollY > 20 && (fabAdd.visibility == View.VISIBLE)) {
//                fabAdd.visibility = View.GONE
//            } else if (previousScrollY - container.scrollY > 20 && fabAdd.visibility != View.VISIBLE) {
//                if (bottom_navigation.selectedItemId == R.id.catalog)
//                    fabAdd.show()
//            }
//
//            previousScrollY = container.scrollY
//        }

        bottom_navigation.selectedItemId = R.id.dashboard

        notification.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        ivNotification.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
        ivCalculate.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }
    }
}