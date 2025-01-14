package com.unistra.m2info.composemymeal

import com.unistra.m2info.composemymeal.api.MealApiService
import com.unistra.m2info.composemymeal.api.MealDetail
import com.unistra.m2info.composemymeal.api.MealDetailsResponse
import com.unistra.m2info.composemymeal.api.RetrofitInstance
import com.unistra.m2info.composemymeal.viewmodels.SearchMealViewModel
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

@OptIn(ExperimentalCoroutinesApi::class)
class SearchMealViewModelTest {

    private lateinit var viewModel: SearchMealViewModel
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

        viewModel = SearchMealViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `fetchAllMeals uses cachedMeals if available and forceRefresh is false`() {
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
            )

        )
        viewModel.cachedMeals = cachedMeals

        viewModel.fetchAllMeals(forceRefresh = false)

        assertEquals(cachedMeals, viewModel.meals.value)
        assertEquals(cachedMeals, viewModel.filteredMeals.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchAllMeals uses cached meals when available`() {
        val cachedMeals = listOf(
            MealDetail(
                idMeal = "1",
                strMeal = "Apple pie",
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
                strMeal = "Banana",
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

        viewModel.cachedMeals = cachedMeals

        viewModel.fetchAllMeals()

        assertEquals(cachedMeals, viewModel.meals.value)
        assertEquals(cachedMeals, viewModel.filteredMeals.value)
    }

    @Test
    fun `fetchAllMeals handles API failure gracefully`() {
        every { mockApi.getMealsByFirstLetter(any()) } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = it.invocation.args[0] as Callback<MealDetailsResponse>
            callback.onFailure(mockCall, Throwable("Network error"))
        }

        viewModel.fetchAllMeals()

        assertFalse(viewModel.isLoading.value)
        assertTrue(viewModel.meals.value.isEmpty())
        assertTrue(viewModel.filteredMeals.value.isEmpty())
        assertTrue(viewModel.cachedMeals.isNullOrEmpty())
    }

}
