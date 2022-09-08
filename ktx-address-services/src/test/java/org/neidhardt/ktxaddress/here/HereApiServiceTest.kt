package org.neidhardt.ktxaddress.here

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.util.*

@ExperimentalCoroutinesApi
class HereApiServiceTest {

	private lateinit var apiKey: String

	@Before
	fun readApiKey() {
		val localProperties = Properties()
		localProperties.load(FileInputStream("../local.properties"))
		apiKey = localProperties.getProperty("hereApiKey")
	}

	@Test
	fun getSearchResults() = runTest {
		// arrange
		val unit = HereApiService(apiKey)
		// action
		val result = unit.getLocationsForQuery("berlin").first()
		// verify
		assertTrue(result.isNotEmpty())
	}

	@Test
	fun getSearchResults_emptyQuery() = runTest {
		// arrange
		val unit = HereApiService(apiKey)
		// action
		val result = unit.getLocationsForQuery("").first()
		// verify
		assertTrue(result.isEmpty())
	}
}