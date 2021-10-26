/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.rxaddress.here

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.neidhardt.rxaddress.here.model.GeoCode
import org.neidhardt.rxaddress.here.model.HereGeoCodeResult
import org.neidhardt.rxaddress.here.model.HereSuggestResult
import org.neidhardt.rxaddress.here.model.Suggestion
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private interface HereSuggestionApi {
	@GET("suggest.json?maxresults=20&country=DEU")
	fun suggest(
		@Query("apiKey") apiKey: String,
		@Query("query") query: String
	): Single<HereSuggestResult>
}

private interface HereGeoCoderApi {
	@GET("geocode.json?jsonattributes=1&gen=9")
	fun geocode(
		@Query("apiKey") apiKey: String,
		@Query("locationid") locationId: String
	): Single<HereGeoCodeResult>
}

/**
 * HereApiService provides an rx wrapper for the HERE API.
 * Data is retrieved using retrofit. It uses the io scheduler by default.
 *
 * @property apiKey A valid key for HERE.
 */
class HereApiService(
	private val apiKey: String
) {

	private val hereSuggest = Retrofit.Builder()
		.baseUrl("https://autocomplete.geocoder.ls.hereapi.com/6.2/")
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // return rx observable
		.addConverterFactory(GsonConverterFactory.create()) // use gson for serialization
		.build()
		.create(HereSuggestionApi::class.java)

	private val hereGeoCode = Retrofit.Builder()
		.baseUrl("https://geocoder.ls.hereapi.com/6.2/")
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // return rx observable
		.addConverterFactory(GsonConverterFactory.create()) // use gson for serialization
		.build()
		.create(HereGeoCoderApi::class.java)

	/**
	 * getSuggestResult returns a suggestion result for a given query string.
	 * Useful for implementing address search functionality.
	 *
	 * @param query The query to obtain addresses for.
	 * @return Suggestion result
	 */
	fun getSuggestResult(query: String): Single<HereSuggestResult> {
		return hereSuggest.suggest(apiKey, query).subscribeOn(Schedulers.io())
	}

	/**
	 * getGeoCodeResult returns location details for a given location id.
	 *
	 * @param locationId Id for location to lock up.
	 * @return Geo coding result.
	 */
	fun getGeoCodeResult(locationId: String): Single<HereGeoCodeResult> {
		return hereGeoCode.geocode(apiKey, locationId).subscribeOn(Schedulers.io())
	}

	/**
	 * getLocationsForQuery returns a list of suggestions and location details for a given query.
	 * This is convenient, because a here suggestion (for strange reason) does not contain the actual geoPosition.
	 *
	 * @param query The query to obtain addresses for.
	 * @return List of pairs, containing a suggestion and matching geocode.
	 */
	fun getLocationsForQuery(query: String): Single<List<Pair<Suggestion,GeoCode>>> {
		return getSuggestResult(query)
			.flattenAsObservable { it.suggestions }
			.filter { it.label != null }
			.flatMapSingle { suggestionResult ->
				getGeoCodeResult(suggestionResult.locationId)
					.map { geoCodingResult ->
						Pair(suggestionResult, geoCodingResult.response)
					}
			}
			.filter { pair ->
				pair.second.view.firstOrNull()?.result
					?.firstOrNull()?.location?.displayPosition != null
			}
			.toList()
	}
}
