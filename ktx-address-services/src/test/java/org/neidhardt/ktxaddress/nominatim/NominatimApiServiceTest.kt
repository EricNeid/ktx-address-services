package org.neidhardt.ktxaddress.nominatim

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class NominatimApiServiceTest {

	@Test
	fun getSearchResults() = runTest {
		// arrange
		val unit = NominatimApiService()
		// action
		val result = unit.getSearchResults("berlin").first()
		// verify
		assertTrue(result.isNotEmpty())
	}
}