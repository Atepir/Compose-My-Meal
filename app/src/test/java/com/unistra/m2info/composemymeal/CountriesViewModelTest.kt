package com.unistra.m2info.composemymeal

import com.unistra.m2info.composemymeal.api.CountryListResponse
import com.unistra.m2info.composemymeal.api.IngredientListResponse
import com.unistra.m2info.composemymeal.api.MealApiService
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import com.unistra.m2info.composemymeal.viewmodels.CountriesViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesViewModelTest {
    private lateinit var viewModel: CountriesViewModel
    private lateinit var mockApi: MealApiService
    private lateinit var mockCall: Call<CountryListResponse>
    private lateinit var mockMealsCall: Call<MealDetailsResponse>
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockApi = mockk()
        mockCall = mockk()
        mockMealsCall = mockk()
        mockkObject(RetrofitInstance)
        every { RetrofitInstance.api } returns mockApi

        viewModel = CountriesViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `fetchCountries uses cache if available and forceRefresh is false`() {
        val cachedCountries = listOf("Italy", "France", "USA")

        val cacheField = CountriesViewModel::class.java.getDeclaredField("countriesCache")
        cacheField.isAccessible = true
        (cacheField.get(viewModel) as MutableList<String>).addAll(cachedCountries)

        viewModel.fetchCountries(forceRefresh = false)

        assertEquals(cachedCountries, viewModel.countries.value)
    }

    @Test
    fun `fetchMealsByCountry uses cache if available and forceRefresh is false`() {
        val cachedMeals = listOf(
            MealDetail(
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
            ),
            MealDetail(
                idMeal = "2",
                strMeal = "Pasta",
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
        )

        val cacheField = CountriesViewModel::class.java.getDeclaredField("mealsByCountryCache")
        cacheField.isAccessible = true
        (cacheField.get(viewModel) as MutableMap<String, List<MealDetail>>)["Italy"] = cachedMeals

        viewModel.fetchMealsByCountry("Italy", forceRefresh = false)

        assertEquals(cachedMeals, viewModel.meals.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchMealsByCountry fetches data from API when cache is empty`() {
        val mockMeals = listOf(
            MealDetail(
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
            ),
            MealDetail(
                idMeal = "2",
                strMeal = "Pasta",
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
        )

        every { mockApi.getMealsByCountry("Italy") } returns mockMealsCall
        every { mockMealsCall.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<MealDetailsResponse>
            callback.onResponse(mockMealsCall, Response.success(MealDetailsResponse(mockMeals)))
        }

        viewModel.fetchMealsByCountry("Italy", forceRefresh = true)

        assertEquals(mockMeals, viewModel.meals.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchMealsByCountry handles API failure gracefully`() {
        every { mockApi.getMealsByCountry("Italy") } returns mockMealsCall
        every { mockMealsCall.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<MealDetailsResponse>
            callback.onFailure(mockMealsCall, Throwable("Network error"))
        }

        viewModel.fetchMealsByCountry("Italy", forceRefresh = true)

        assertTrue(viewModel.meals.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchCountries handles API failure gracefully`() {
        every { mockApi.getCountries() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<CountryListResponse>
            callback.onFailure(mockCall, Throwable("Network error"))
        }

        viewModel.fetchCountries(forceRefresh = true)

        assertTrue(viewModel.countries.value.isEmpty())
    }
}