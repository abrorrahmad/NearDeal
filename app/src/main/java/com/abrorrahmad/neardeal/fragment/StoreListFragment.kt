package com.abrorrahmad.neardeal.fragment

import Store
import StoreRespone
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.abrorrahmad.neardeal.MapsActivity
import com.abrorrahmad.neardeal.ProductActivity
import com.abrorrahmad.neardeal.R
import com.abrorrahmad.neardeal.adapter.StoreitemAdapter
import com.abrorrahmad.neardeal.data.ApiClient
import com.abrorrahmad.neardeal.data.ApiEndpoint
import com.abrorrahmad.neardeal.util.PopupUtil
import kotlinx.android.synthetic.main.fragment_store.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoreListFragment : Fragment(), StoreitemAdapter.OnItemClickListener {

    private lateinit var list: ArrayList<Store>
    private lateinit var storeAdapter: StoreitemAdapter

    private var lat = 0.0
    private var lng = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val bundleArguments = arguments

//        if (bundleArguments != null){
//
//        }
        bundleArguments?.let {
            lat = it.getDouble(KEY_LAT)
            lng = it.getDouble(KEY_LNG)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        storeAdapter = StoreitemAdapter(list)
        recView.adapter = storeAdapter
        recView.layoutManager = LinearLayoutManager(context)

        storeAdapter?.setListener(this)

        loadStores()
    }

    fun loadStores(){

        val api = ApiClient.getClient(requireContext())
        val apiEndpoint = api.create(ApiEndpoint::class.java)

        val callGetStore = apiEndpoint.getListStore(lat, lng)

        PopupUtil.showLoading(activity,"Loading","Loading Store..")

        callGetStore.enqueue(
            object : Callback<StoreRespone>{
                override fun onResponse(
                    call: Call<StoreRespone>,
                    response: Response<StoreRespone>
                ) {
                    PopupUtil.dismissDialog()
                    //kalau dapat balikan
                    if (!response.isSuccessful){
                        Log.d("StoreListFragment", response.toString())
                        return
                    }
                    if (response.body() != null){

                        val storeRespone : StoreRespone? = response.body()
                        Log.d("StoreListFragment", "Jumlah pada db ada"
                        +storeRespone?.store?.size)

                        if (storeRespone!!.store.size > 0){
                            recView.visibility = View.VISIBLE
                            tvEmpty.visibility = View.GONE

                            list.addAll(storeRespone.store)
                            storeAdapter.notifyDataSetChanged()

                        }else{
                            recView.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                        }

                    }
                }

                override fun onFailure(call: Call<StoreRespone>, t: Throwable) {
                    //akan di eksekusi
                    PopupUtil.dismissDialog()
                    Log.d("StoreListFragment", t.toString())
                }

            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_maps){
            val intentMaps = Intent(activity, MapsActivity::class.java)

            intentMaps.putExtra(KEY_LAT, lat)
            intentMaps.putExtra(KEY_LNG, lng)
            intentMaps.putParcelableArrayListExtra(MapsActivity.KEY_STORE , list)

            startActivity(intentMaps)
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.store_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object{
        const val  KEY_LAT = "keylat"
        const val KEY_LNG = "keylng"

    }

    override fun onItemClick(storeId: String) {

        val i = Intent(activity, ProductActivity::class.java)
        i.putExtra(ProductActivity.STORE_ID, storeId)
        startActivity(i)
    }


}