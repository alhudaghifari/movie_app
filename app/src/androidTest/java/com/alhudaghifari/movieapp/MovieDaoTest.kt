package com.alhudaghifari.movieapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alhudaghifari.movieapp.db.AppDatabase
import com.alhudaghifari.movieapp.db.dao.MovieDao
import com.alhudaghifari.movieapp.db.entity.Movie
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var movieDao: MovieDao
    val movie1 = Movie(1, "Jun dan Kopi", "2020-04-02", "Jun dan Kopi adalah hewan peliharaan", "posterPath")
    val movie2 = Movie(2, "B", "2020-04-04", "description", "posterPath2")
    val movie3 = Movie(3, "C", "2020-04-03", "mantap", "posterPath3")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        movieDao = database.movieDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        movieDao.insertAll(listOf(movie1, movie2, movie3))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetMovie() = runBlocking {
        val movieList = movieDao.getAll()
        Assert.assertThat(movieList.size, Matchers.equalTo(3))

        // Ensure plant list is sorted by name
        Assert.assertThat(movieList[0], Matchers.equalTo(movie1))
        Assert.assertThat(movieList[1], Matchers.equalTo(movie2))
        Assert.assertThat(movieList[2], Matchers.equalTo(movie3))
    }

    @Test
    fun testGetFavorite() = runBlocking {
        Assert.assertThat(movieDao.getDataById(1).first(), Matchers.equalTo(movie1))
    }

}