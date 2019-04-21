package br.com.adalbertofjr.hotel.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import br.com.adalbertofjr.hotel.*
import br.com.adalbertofjr.hotel.details.HotelDetailsActivity
import br.com.adalbertofjr.hotel.details.HotelDetailsFragment
import br.com.adalbertofjr.hotel.model.Hotel
import br.com.adalbertofjr.hotel.form.HotelFormFragment
import br.com.adalbertofjr.hotel.list.HotelListFragment
import kotlinx.android.synthetic.main.activity_hotel.*

class HotelActivity : AppCompatActivity(),
    HotelListFragment.OnHotelClickListener,
    HotelListFragment.OnHotelDeletedListener,
    SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener,
    HotelFormFragment.OnHotelSavedListener {

    private var hotelIdSelected: Long = -1
    private var lastSearchTerm: String = ""
    private var searchView: SearchView? = null
    private val listFragment: HotelListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as HotelListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        fabAdd.setOnClickListener {
            listFragment.hideDeleteMode()
            HotelFormFragment.newInstance().open(supportFragmentManager)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(EXTRA_SEARCH_TERM, lastSearchTerm)
        outState?.putLong(EXTRA_HOTEL_ID_SELECTED, hotelIdSelected)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        hotelIdSelected = savedInstanceState?.getLong(EXTRA_HOTEL_ID_SELECTED) ?: 0
        lastSearchTerm = savedInstanceState?.getString(EXTRA_SEARCH_TERM) ?: ""
    }

    override fun onHotelClick(hotel: Hotel) {
        if (isTablet()) {
            hotelIdSelected = hotel.id
            showDetailsFragment(hotel.id)
        } else {
            showDetailsActivity(hotel.id)
        }
    }

    private fun showDetailsFragment(hotelId: Long) {
        searchView?.setOnQueryTextListener(null) // removendo listener para quando a tela de detalhe for recriada

        val fragment = HotelDetailsFragment.newInstance(hotelId)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.details, fragment,
                HotelDetailsFragment.TAG_DETAILS
            )
            .commit()

    }

    private fun isTablet(): Boolean = resources.getBoolean(R.bool.tablet)

    private fun showDetailsActivity(hotelId: Long) {
        HotelDetailsActivity.open(this, hotelId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.hint_search)
        searchView.setOnQueryTextListener(this)
        if (lastSearchTerm.isNullOrEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_info -> AboutDialogFragment().show(supportFragmentManager, "sobre")
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        listFragment.search(lastSearchTerm)
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem?) = true

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch()
        return true
    }

    override fun onHotelSaved(hotel: Hotel) {
        listFragment.search(lastSearchTerm)
    }

    override fun onHotelsDeleted(hotels: List<Hotel>) {
        if (hotels.find { it.id == hotelIdSelected } != null){
            val fragment = supportFragmentManager.findFragmentByTag(HotelDetailsFragment.TAG_DETAILS)
            if (fragment != null){
                supportFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit()
            }
        }
    }

    companion object {
        private val EXTRA_SEARCH_TERM = "lastSearch"
        private val EXTRA_HOTEL_ID_SELECTED = "lastSelectedId"
    }
}
