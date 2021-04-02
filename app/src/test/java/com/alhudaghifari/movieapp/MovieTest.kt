package com.alhudaghifari.movieapp

import com.alhudaghifari.movieapp.db.entity.Movie
import junit.framework.Assert.assertEquals
import org.junit.Test

class MovieTest {
    @Test fun test_default_value() {
        val movie = Movie(1, "Jun dan Kopi", "2020-04-02", "Jun dan Kopi adalah hewan peliharaan", "posterPath")
        assertEquals("2020-04-02",movie.releasedDate)
        assertEquals(1, movie.id)
    }
}