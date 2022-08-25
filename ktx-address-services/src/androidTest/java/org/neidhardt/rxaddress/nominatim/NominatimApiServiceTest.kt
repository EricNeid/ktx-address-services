package org.neidhardt.rxaddress.nominatim

import org.junit.Assert.*
import org.junit.Test

class NominatimApiServiceTest {

	@Test
	fun getSearchResults() {
		// arrange
		val unit = NominatimApiService()
		// action
		val result = unit.getSearchResults("berlin").blockingGet()
		// verify
		assertNotNull(result)
		assertTrue(result.isNotEmpty())
	}
}