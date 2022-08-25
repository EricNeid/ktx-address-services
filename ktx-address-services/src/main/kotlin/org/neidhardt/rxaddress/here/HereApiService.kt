/*
 * SPDX-FileCopyrightText: 2021 Eric Neidhardt
 * SPDX-License-Identifier: MIT
 */
package org.neidhardt.rxaddress.here

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.neidhardt.rxaddress.here.model.GeoCode
import org.neidhardt.rxaddress.here.model.HereGeoCodeResult
import org.neidhardt.rxaddress.here.model.HereSuggestResult
import org.neidhardt.rxaddress.here.model.Suggestion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private interface HereSuggestionApi {
	@GET("suggest.json?maxresults=20&country=DEU")
	suspend fun suggest(
		@Query("apiKey") apiKey: String,
		@Query("query") query: String
	): HereSuggestResult
}

private interface HereGeoCoderApi {
	@GET("geocode.json?jsonattributes=1&gen=9")
	suspend fun geocode(
		@Query("apiKey") apiKey: String,
		@Query("locationid") locationId: String
	): HereGeoCodeResult
}

/**
 * HereApiService provides an rx wrapper for the HERE API.
 * Data is retrieved using retrofit.
 *
 * @property apiKey A valid key for HERE.
 */
class HereApiService(
	private val apiKey: String
) {

	var baseUrlSuggestionApi = "https://autocomplete.geocoder.ls.hereapi.com/6.2/"
	var baseUrlGeoCoderApi = "https://geocoder.ls.hereapi.com/6.2/"
	var dispatcher = Dispatchers.IO

	private val hereSuggest = Retrofit.Builder()
		.baseUrl(baseUrlSuggestionApi)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(HereSuggestionApi::class.java)

	private val hereGeoCode = Retrofit.Builder()
		.baseUrl(baseUrlGeoCoderApi)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(HereGeoCoderApi::class.java)

	/**
	 * getSuggestResult returns a suggestion result for a given query string.
	 * Useful for implementing address search functionality.
	 *
	 * @param query The query to obtain addresses for.
	 * @return Suggestion result
	 */
	fun getSuggestResult(query: String): Flow<HereSuggestResult> {
		return flow {
			val result = hereSuggest.suggest(apiKey, query)
			emit(result)
		}.flowOn(dispatcher)
	}

	/**
	 * getGeoCodeResult returns location details for a given location id.
	 *
	 * @param locationId Id for location to lock up.
	 * @return Geo coding result.
	 */
	fun getGeoCodeResult(locationId: String): Flow<HereGeoCodeResult> {
		return flow {
			val result = hereGeoCode.geocode(apiKey, locationId)
			emit(result)
		}.flowOn(dispatcher)
	}

	/**
	 * getLocationsForQuery returns a list of suggestions and location details for a given query.
	 * This is convenient, because a here suggestion (for strange reason) does not contain the actual geoPosition.
	 *
	 * @param query The query to obtain addresses for.
	 * @return List of pairs, containing a suggestion and matching geocode.
	 */
	@OptIn(FlowPreview::class)
	fun getLocationsForQuery(query: String): Flow<List<Pair<Suggestion,GeoCode>>> {
		return getSuggestResult(query)
			.map { it.suggestions }
			.map { it -> it?.filter { it.label != null } ?: emptyList() }
			.flatMapConcat { suggestions ->
				flow {
					val result = suggestions.map { suggestion ->
						getGeoCodeResult(suggestion.locationId)
							.map { geoCodingResult -> Pair(suggestion, geoCodingResult.response) }
							.first()
					}
					emit(result)
				}
			}
	}
}
