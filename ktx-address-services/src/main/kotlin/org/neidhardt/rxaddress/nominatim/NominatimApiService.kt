/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.rxaddress.nominatim

import io.reactivex.rxjava3.core.Single
import org.neidhardt.rxaddress.nominatim.model.SearchResult
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private interface NominatimApi {
	@GET("search?format=jsonv2")
	fun search(@Query("q") query: String): Single<List<SearchResult>>
}

class NominatimApiService {

	private val nominatimApi: NominatimApi = Retrofit.Builder()
		.baseUrl("https://nominatim.openstreetmap.org/")
		.addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // return rx observable
		.addConverterFactory(GsonConverterFactory.create()) // use gson for serialization
		.build()
		.create(NominatimApi::class.java)

	/**
	 * getSearchResults returns address information from Nominatim.
	 * It uses RX and Retrofit to get the data.
	 *
	 * @param query The query to obtain addresses for.
	 * @return List of search results.
	 */
	fun getSearchResults(query: String): Single<List<SearchResult>> {
		return nominatimApi.search(query)
	}
}