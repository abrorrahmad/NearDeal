package com.abrorrahmad.neardeal

import ProductDetailRespone
import android.content.Context
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.abrorrahmad.neardeal.fragment.DealFragment
import com.abrorrahmad.neardeal.fragment.ProductFragment


class ProductActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        //obejct secstion pageadapter yaitu inner class
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)


        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter


        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }
    companion object{
        const val STORE_ID = "store_id"
    }

    inner class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val productListFragment : ProductFragment
        private val dealFragment : DealFragment

        init {
            productListFragment = ProductFragment()
            dealFragment = DealFragment()


            val storeID: String? = intent.getStringExtra(STORE_ID)

            val argument = Bundle()
            argument.putString(STORE_ID, storeID)
            productListFragment.arguments = argument
            dealFragment.arguments = argument
        }

        override fun getItem(position: Int): Fragment {
            return if (position == 0){
                productListFragment
            }else{
                dealFragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {

            when (position){
                0 -> return "PRODUCT"
                1 -> return "DEAL"
                else-> return null
            }
        }



        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }


    }
}