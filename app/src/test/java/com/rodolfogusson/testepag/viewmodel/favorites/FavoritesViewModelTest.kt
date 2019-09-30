package com.rodolfogusson.testepag.viewmodel.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rodolfogusson.testepag.TestHelper
import com.rodolfogusson.testepag.infrastructure.data.repository.FavoritesRepository
import com.rodolfogusson.testepag.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoritesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoritesViewModel

    private val favoritesRepository: FavoritesRepository = mock()

    private val favoritesLiveData = MutableLiveData<List<Movie>>()

    private val testHelper = TestHelper()

    private val moviesList = testHelper.moviesList

    @Before
    fun setup() {
        favoritesLiveData.value = moviesList
        whenever(favoritesRepository.getFavorites()).thenReturn(favoritesLiveData)
        viewModel = FavoritesViewModel(favoritesRepository)
    }

    @Test
    fun `favorites should getAllFavorites from repository`() {
        //WHEN
        //viewModel inits

        //THEN
        verify(favoritesRepository).getFavorites()

        val favorites = viewModel.favorites
            .test()
            .awaitValue()
            .value()

        assertEquals(moviesList, favorites)
    }
}