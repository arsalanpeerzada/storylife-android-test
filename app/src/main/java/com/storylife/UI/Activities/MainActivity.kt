package com.storylife.UI.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.storylife.R
import com.storylife.UI.Fragments.Artwork
import com.storylife.UI.Fragments.Chat
import com.storylife.UI.Fragments.Journal
import com.storylife.UI.Fragments.MoreInfo
import com.storylife.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

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
                if (position == 0) {
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
                } else if (position == 1) {
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
                    );
                } else if (position == 2) {
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
                } else if (position == 3) {
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
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.journal.setOnClickListener {
            vpPager.currentItem = 0

            binding.journal.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appBlue
                )
            );
            binding.chat.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.doodle.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.more.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );

        }
        binding.chat.setOnClickListener {
            binding.chat.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appBlue
                )
            );
            binding.journal.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.doodle.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.more.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            vpPager.currentItem = 1
        }
        binding.doodle.setOnClickListener {

            binding.doodle.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appBlue
                )
            );
            binding.journal.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.chat.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.more.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );

            vpPager.currentItem = 2
        }
        binding.more.setOnClickListener {


            binding.more.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appBlue
                )
            );
            binding.journal.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.doodle.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            binding.chat.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.appgrey
                )
            );
            vpPager.currentItem = 3
        }


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
                3 -> MoreInfo()

                else -> null!!
            }
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence? {
            return "$position"
        }

        companion object {
            private const val NUM_ITEMS = 4
        }
    }
}