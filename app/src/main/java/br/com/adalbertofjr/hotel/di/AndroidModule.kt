package br.com.adalbertofjr.hotel.di

import br.com.adalbertofjr.hotel.details.HotelDetailsPresenter
import br.com.adalbertofjr.hotel.details.HotelDetailsView
import br.com.adalbertofjr.hotel.form.HotelFormPresenter
import br.com.adalbertofjr.hotel.form.HotelFormView
import br.com.adalbertofjr.hotel.list.HotelListPresenter
import br.com.adalbertofjr.hotel.list.HotelListView
import br.com.adalbertofjr.hotel.repository.HotelRepository
import br.com.adalbertofjr.hotel.repository.sqlite.ProviderRepository
import br.com.adalbertofjr.hotel.repository.sqlite.SQLiteRepository
import org.koin.dsl.module

val androidModule = module {

    single { this }
    single {
        ProviderRepository(ctx = get()) as HotelRepository
    }

    factory { (view: HotelListView) ->
        HotelListPresenter(view, repository = get())
    }

    factory { (view: HotelDetailsView) ->
        HotelDetailsPresenter(view, repository = get())
    }

    factory { (view: HotelFormView) ->
        HotelFormPresenter(view, repository = get())
    }

}