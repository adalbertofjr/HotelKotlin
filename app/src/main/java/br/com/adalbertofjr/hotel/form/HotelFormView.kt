package br.com.adalbertofjr.hotel.form

import br.com.adalbertofjr.hotel.model.Hotel

interface HotelFormView {
    fun showHotel(hotel: Hotel)
    fun errorInvalidHotel()
    fun errorSaveHotel()
}
