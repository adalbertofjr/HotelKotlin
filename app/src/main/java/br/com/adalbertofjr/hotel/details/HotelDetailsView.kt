package br.com.adalbertofjr.hotel.details

import br.com.adalbertofjr.hotel.model.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelNotFound()
}