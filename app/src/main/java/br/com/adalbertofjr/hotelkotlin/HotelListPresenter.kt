package br.com.adalbertofjr.hotelkotlin

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {

    fun searchHotels(term: String) {
        repository.search(term) { hotels ->
            view.showHotel(hotels)
        }
    }

    fun showHotelDetails(hotel: Hotel) {
        view.showHotelDetails(hotel)
    }
}