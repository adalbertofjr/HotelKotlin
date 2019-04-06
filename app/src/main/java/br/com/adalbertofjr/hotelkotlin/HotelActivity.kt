package br.com.adalbertofjr.hotelkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HotelActivity : AppCompatActivity(), HotelListFragment.OnHotelClickListener {

    override fun onHotelClick(hotel: Hotel) {
        showDetailsActivity(hotel.id)
    }

    private fun showDetailsActivity(hotelId: Long) {
        HotelDetailsActivity.open(this, hotelId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
    }
}
