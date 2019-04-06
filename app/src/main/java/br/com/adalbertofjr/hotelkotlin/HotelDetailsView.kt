package br.com.adalbertofjr.hotelkotlin

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelNotFound()
}