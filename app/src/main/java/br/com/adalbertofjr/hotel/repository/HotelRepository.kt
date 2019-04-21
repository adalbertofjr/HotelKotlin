package br.com.adalbertofjr.hotel.repository

import br.com.adalbertofjr.hotel.model.Hotel

interface HotelRepository {
    fun save(hotel: Hotel)
    fun remove(vararg hotels: Hotel)
    fun hotelById(id: Long, callback: (Hotel?) -> Unit)
    fun search(term:String, callback: (List<Hotel>) -> Unit)

}