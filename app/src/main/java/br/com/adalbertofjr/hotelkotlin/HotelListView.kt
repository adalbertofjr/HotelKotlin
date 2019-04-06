package br.com.adalbertofjr.hotelkotlin

interface HotelListView {
    fun showHotel(hotels: List<Hotel>)
    fun showHotelDetails(hotel: Hotel)
}