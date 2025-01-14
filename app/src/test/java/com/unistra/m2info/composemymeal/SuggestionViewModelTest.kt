package com.unistra.m2info.composemymeal

import androidx.compose.runtime.mutableStateOf
import com.unistra.m2info.composemymeal.api.MealApiService
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import com.unistra.m2info.composemymeal.viewmodels.SuggestionViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SuggestionViewModelTest {

    private lateinit var viewModel: SuggestionViewModel
    private lateinit var mockApi: MealApiService
    private lateinit var mockCall: Call<MealDetailsResponse>

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockApi = mockk()
        mockCall = mockk()
        mockkObject(RetrofitInstance)
        every { RetrofitInstance.api } returns mockApi

        viewModel = SuggestionViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `fetchRandomMeal uses cachedMeal if available and forceRefresh is false`() {
        val cachedMeal = MealDetail(
            idMeal = "1",
            strMeal = "Pizza",
            strCategory = "Italian",
            strArea = "Italy",
            strInstructions = "Bake in oven at 200 degrees.",
            strMealThumb = "http://example.com/image.jpg",
            strTags = "Main,Dinner",
            strYoutube = "http://youtube.com/example",
            strIngredient1 = "Flour",
            strIngredient2 = "Water",
            strIngredient3 = "Yeast",
            strIngredient4 = "Salt",
            strIngredient5 = "Tomato",
            strIngredient6 = "Cheese",
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strIngredient16 = null,
            strIngredient17 = null,
            strIngredient18 = null,
            strIngredient19 = null,
            strIngredient20 = null,
            strIngredient21 = null,
            strIngredient22 = null,
            strIngredient23 = null,
            strIngredient24 = null,
            strIngredient25 = null,
            strIngredient26 = null,
            strIngredient27 = null,
            strIngredient28 = null,
            strIngredient29 = null,
            strIngredient30 = null,
            strMeasure1 = "2 cups",
            strMeasure2 = "1 cup",
            strMeasure3 = "1 tsp",
            strMeasure4 = "1 tsp",
            strMeasure5 = "1 cup",
            strMeasure6 = "200g",
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strMeasure16 = null,
            strMeasure17 = null,
            strMeasure18 = null,
            strMeasure19 = null,
            strMeasure20 = null,
            strMeasure21 = null,
            strMeasure22 = null,
            strMeasure23 = null,
            strMeasure24 = null,
            strMeasure25 = null,
            strMeasure26 = null,
            strMeasure27 = null,
            strMeasure28 = null,
            strMeasure29 = null,
            strMeasure30 = null
        )
        viewModel.cachedMeal = cachedMeal

        viewModel.fetchRandomMeal(forceRefresh = false)

        assertEquals(cachedMeal, viewModel.randomMeal.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchRandomMeal makes API call if forceRefresh is true`() {
        val mockMeal = MealDetail(
            idMeal = "1",
            strMeal = "Pizza",
            strCategory = "Italian",
            strArea = "Italy",
            strInstructions = "Bake in oven at 200 degrees.",
            strMealThumb = "http://example.com/image.jpg",
            strTags = "Main,Dinner",
            strYoutube = "http://youtube.com/example",
            strIngredient1 = "Flour",
            strIngredient2 = "Water",
            strIngredient3 = "Yeast",
            strIngredient4 = "Salt",
            strIngredient5 = "Tomato",
            strIngredient6 = "Cheese",
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strIngredient16 = null,
            strIngredient17 = null,
            strIngredient18 = null,
            strIngredient19 = null,
            strIngredient20 = null,
            strIngredient21 = null,
            strIngredient22 = null,
            strIngredient23 = null,
            strIngredient24 = null,
            strIngredient25 = null,
            strIngredient26 = null,
            strIngredient27 = null,
            strIngredient28 = null,
            strIngredient29 = null,
            strIngredient30 = null,
            strMeasure1 = "2 cups",
            strMeasure2 = "1 cup",
            strMeasure3 = "1 tsp",
            strMeasure4 = "1 tsp",
            strMeasure5 = "1 cup",
            strMeasure6 = "200g",
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strMeasure16 = null,
            strMeasure17 = null,
            strMeasure18 = null,
            strMeasure19 = null,
            strMeasure20 = null,
            strMeasure21 = null,
            strMeasure22 = null,
            strMeasure23 = null,
            strMeasure24 = null,
            strMeasure25 = null,
            strMeasure26 = null,
            strMeasure27 = null,
            strMeasure28 = null,
            strMeasure29 = null,
            strMeasure30 = null
        )
        val mockResponse = MealDetailsResponse(listOf(mockMeal))

        every { mockApi.getRandomMeal() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<MealDetailsResponse>
            callback.onResponse(mockCall, Response.success(mockResponse))
        }

        viewModel.fetchRandomMeal(forceRefresh = true)

        assertFalse(viewModel.isLoading.value)
        assertEquals(mockMeal, viewModel.randomMeal.value)
        assertEquals(mockMeal, viewModel.cachedMeal)
    }

    @Test
    fun `fetchRandomMeal makes API call if cachedMeal is null`() {
        val mockMeal = MealDetail(
            idMeal = "1",
            strMeal = "Pizza",
            strCategory = "Italian",
            strArea = "Italy",
            strInstructions = "Bake in oven at 200 degrees.",
            strMealThumb = "http://example.com/image.jpg",
            strTags = "Main,Dinner",
            strYoutube = "http://youtube.com/example",
            strIngredient1 = "Flour",
            strIngredient2 = "Water",
            strIngredient3 = "Yeast",
            strIngredient4 = "Salt",
            strIngredient5 = "Tomato",
            strIngredient6 = "Cheese",
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strIngredient16 = null,
            strIngredient17 = null,
            strIngredient18 = null,
            strIngredient19 = null,
            strIngredient20 = null,
            strIngredient21 = null,
            strIngredient22 = null,
            strIngredient23 = null,
            strIngredient24 = null,
            strIngredient25 = null,
            strIngredient26 = null,
            strIngredient27 = null,
            strIngredient28 = null,
            strIngredient29 = null,
            strIngredient30 = null,
            strMeasure1 = "2 cups",
            strMeasure2 = "1 cup",
            strMeasure3 = "1 tsp",
            strMeasure4 = "1 tsp",
            strMeasure5 = "1 cup",
            strMeasure6 = "200g",
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strMeasure16 = null,
            strMeasure17 = null,
            strMeasure18 = null,
            strMeasure19 = null,
            strMeasure20 = null,
            strMeasure21 = null,
            strMeasure22 = null,
            strMeasure23 = null,
            strMeasure24 = null,
            strMeasure25 = null,
            strMeasure26 = null,
            strMeasure27 = null,
            strMeasure28 = null,
            strMeasure29 = null,
            strMeasure30 = null
        )
        val mockResponse = MealDetailsResponse(listOf(mockMeal))

        every { mockApi.getRandomMeal() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<MealDetailsResponse>
            callback.onResponse(mockCall, Response.success(mockResponse))
        }

        viewModel.fetchRandomMeal()

        assertFalse(viewModel.isLoading.value)
        assertEquals(mockMeal, viewModel.randomMeal.value)
        assertEquals(mockMeal, viewModel.cachedMeal)
    }

    @Test
    fun `fetchRandomMeal sets isLoading to false on API failure`() {
        every { mockApi.getRandomMeal() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<MealDetailsResponse>
            callback.onFailure(mockCall, Throwable("Network error"))
        }

        viewModel.fetchRandomMeal()

        assertFalse(viewModel.isLoading.value)
        assertNull(viewModel.randomMeal.value)
    }
}
