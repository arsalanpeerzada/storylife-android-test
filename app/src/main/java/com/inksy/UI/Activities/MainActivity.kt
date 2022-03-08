package com.inksy.UI.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.inksy.UI.Dialogs.TwoButtonDialog
import com.inksy.Interfaces.OnDialogClickListener
import com.inksy.R
import com.inksy.UI.Fragments.*
import com.inksy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private val DOUBLE_PRESS_INTERVAL: Long = 350 // in millis

    private var lastPressTime: Long = 0
    private lateinit var adapterViewPager: MyPagerAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var vpPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        vpPager = binding.vpPager
        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager



        vpPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
selectFragment(position)

            }
            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.journal.setOnClickListener {
            vpPager.currentItem = 0

            selectFragment(0)

        }
        binding.chat.setOnClickListener {

            selectFragment(1)
            vpPager.currentItem = 1
        }
        binding.doodle.setOnClickListener {

            selectFragment(2)
            vpPager.currentItem = 2
        }
        binding.notification.setOnClickListener() {

            selectFragment(3)
            vpPager.currentItem = 3
        }

        binding.more.setOnClickListener {

            selectFragment(4)
            vpPager.currentItem = 4
        }
    }

    override fun onBackPressed() {

        if (vpPager.currentItem == 0) {
            val pressTime = System.currentTimeMillis()
            if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
                openDialog()

            } else {
                Toast.makeText(
                    getApplicationContext(),
                    "Click one more time for Exit",
                    Toast.LENGTH_SHORT
                ).show()
            }
            lastPressTime = pressTime;
        }else {
            vpPager.currentItem = 0

            selectFragment(0)
        }

    }

    private fun openDialog() {
        val twoButtonDialog: TwoButtonDialog = TwoButtonDialog(
            this, "Close Application",
            "Are you sure, You want to close this application?",
            getString(android.R.string.yes),
            getString(android.R.string.no),
            object : OnDialogClickListener {
                override fun onDialogClick(callBack: String?) {
                    if (callBack == "Yes") {
                        this@MainActivity.finishAndRemoveTask();
                    } else {

                    }
                }
            })
        twoButtonDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        twoButtonDialog.show()
    }


    class MyPagerAdapter(fragmentManager: FragmentManager?) :
        FragmentPagerAdapter(fragmentManager!!) {
        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_ITEMS
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> Journal()
                1 -> Chat()
                2 -> Artwork()
                3 -> Notifications_Fragment()
                4 -> MoreInfo()

                else -> null!!
            }
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence? {
            return "$position"
        }

        companion object {
            private const val NUM_ITEMS = 5
        }
    }

    private fun selectFragment(position: Int) {
        when (position) {
            0 -> {
                binding.journal.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appBlue
                    )
                );
                binding.chat.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.doodle.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.more.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.notification.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
            }
            1 -> {
                binding.chat.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appBlue
                    )
                );
                binding.journal.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.doodle.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.more.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );binding.notification.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );

            }
            2 -> {
                binding.doodle.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appBlue
                    )
                );
                binding.journal.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.chat.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.more.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.notification.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
            }
            4 -> {
                binding.more.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appBlue
                    )
                );
                binding.journal.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.doodle.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.chat.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.notification.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
            }
            3 -> {
                binding.notification.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appBlue
                    )
                );
                binding.journal.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.doodle.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.chat.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
                binding.more.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.appgrey
                    )
                );
            }
        }
    }
}