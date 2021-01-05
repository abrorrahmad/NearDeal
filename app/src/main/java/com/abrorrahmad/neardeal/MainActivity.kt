package com.abrorrahmad.neardeal

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.abrorrahmad.neardeal.fragment.StoreListFragment
import com.abrorrahmad.neardeal.util.PopupUtil
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, LocationListener{

    private lateinit var mLocationManager : LocationManager
    private lateinit var location : Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val drawerToogle = ActionBarDrawerToggle(this, drawerLayout ,
        toolbar , R.string.open, R.string.close)

        drawerLayout.addDrawerListener(drawerToogle)
        drawerToogle.syncState()

        navView.setNavigationItemSelectedListener(this)
        
        //loadStoreListFragment()
// LOAD STORE PRAGMENT
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.CAMERA) , REQUEST_FINE_LOC_CODE)

                return
            }
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10,
                10f,
                this
        )
        PopupUtil.showLoading(this, "LOADING", "Loading location")

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_FINE_LOC_CODE) {

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //membuka camera
            }

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


                mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        10,
                        10f,
                        this
                )
                    PopupUtil.showLoading(this, "LOADING", "Loading location")

                }else{
                    //tampilkan bahwa permesion nya penting
                }

            }else{
                AlertDialog.Builder(this)
                        .setMessage("Permesion , Please Cek Permesion")
                        .setTitle("Warning")
                        .setPositiveButton("OK", {
                            dialog, whictButton ->
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.CAMERA) , REQUEST_FINE_LOC_CODE)

                        })
                        .setNegativeButton("NO",null)
                        .create().show()

            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_store){

        }
        if (item.itemId == R.id.item_contoh){
            Toast.makeText(this, "Item Contoh"
            , Toast.LENGTH_LONG).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun loadStoreListFragment() {

        val bundle = Bundle()
        bundle.putDouble(StoreListFragment.KEY_LAT,this.location.latitude)
        bundle.putDouble(StoreListFragment.KEY_LNG,this.location.longitude)

        val storeListFragment = StoreListFragment()
        storeListFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, storeListFragment).commit()
    }
    companion object{
        const val REQUEST_FINE_LOC_CODE =123
    }

    override fun onLocationChanged(location: Location) {
        PopupUtil.dismissDialog()
        this.location = location
        mLocationManager.removeUpdates(this)
        Toast.makeText(this,"Lat = ${location.latitude} Lng = ${location.longitude}"
        , Toast.LENGTH_LONG).show()

       loadStoreListFragment()
    }

}