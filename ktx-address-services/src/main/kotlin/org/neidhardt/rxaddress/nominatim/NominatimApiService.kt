/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.rxaddress.nominatim
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import org.neidhardt.rxaddress.nominatim.model.SearchResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private interface NominatimApi {
	@GET("search?format=jsonv2")
	suspend fun search(@Query("q") query: String): List<SearchResult>
}

class NominatimApiService {

	var baseUrlNominatimApi = "https://nominatim.openstreetmap.org/"
	var dispatcher = Dispatchers.IO
	var httpClient = OkHttpClient()

	private val nominatimApi: NominatimApi = Retrofit.Builder()
		.baseUrl(baseUrlNominatimApi)
		.client(httpClient)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(NominatimApi::class.java)

	/**
	 * getSearchResults returns address information from Nominatim.
	 * It uses RX and Retrofit to get the data.
	 *
	 * @param query The query to obtain addresses for.
	 * @return List of search results.
	 */
	fun getSearchResults(query: String): Flow<List<SearchResult>> {
		return flow {
			val result = nominatimApi.search(query)
			emit(result)
		}.flowOn(dispatcher)
	}
}