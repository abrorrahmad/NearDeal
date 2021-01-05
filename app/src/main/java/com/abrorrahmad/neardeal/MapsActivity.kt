package com.abrorrahmad.neardeal

import Store
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.abrorrahmad.neardeal.fragment.StoreListFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var lat = 0.0
    private var lng = 0.0

    private var storeList : List<Store>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lat = intent.getDoubleExtra(StoreListFragment.KEY_LAT,0.0)
        lng = intent.getDoubleExtra(StoreListFragment.KEY_LNG,0.0)
        storeList = intent.getParcelableArrayListExtra(KEY_STORE)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-6.3883743, 106.8257269)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))

        val monas = LatLng (-6.175259, 106.827163)
        mMap.addMarker(MarkerOptions().position(monas).title("ini marker Monas"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monas, 15f))


        val myLocation = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(myLocation).title("Your Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))

        drawStore()
    }

    fun drawStore(){
        storeList?.let {
            list ->

            for (index in storeList!!.indices){
                val store = list[index]

                val latLngStore = LatLng(
                    store.lat, store.lng
                )
                val markerOpt = MarkerOptions()
                    .position(latLngStore)
                    .title(store.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.store))

                mMap.addMarker(markerOpt)

            }
        }
    }

    companion object{
        const val KEY_STORE = "Kye_store1234"
    }
}