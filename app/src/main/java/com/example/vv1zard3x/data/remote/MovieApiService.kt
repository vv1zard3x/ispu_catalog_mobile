package com.example.vv1zard3x.data.remote

import com.example.vv1zard3x.data.remote.dto.ActorDetailDto
import com.example.vv1zard3x.data.remote.dto.ActorDto
import com.example.vv1zard3x.data.remote.dto.ActorsResponse
import com.example.vv1zard3x.data.remote.dto.GenreDto
import com.example.vv1zard3x.data.remote.dto.MovieDto
import com.example.vv1zard3x.data.remote.dto.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API интерфейс для Django бэкенда
 */
interface MovieApiService {
    
    // ===== MOVIES =====
    
    @GET("api/movies/")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
        @Query("genre") genreId: Int? = null,
        @Query("actor") actorId: Int? = null,
        @Query("year") year: Int? = null,
        @Query("min_rating") minRating: Float? = null,
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String = "-rating"
    ): MoviesResponse
    
    @GET("api/movies/{id}/")
    suspend fun getMovieDetails(@Path("id") movieId: Int): MovieDto
    
    @GET("api/movies/{id}/cast/")
    suspend fun getMovieCast(@Path("id") movieId: Int): List<ActorDto>
    
    // ===== GENRES =====
    
    @GET("api/genres/")
    suspend fun getGenres(@Query("search") search: String? = null): List<GenreDto>
    
    // ===== ACTORS =====
    
    @GET("api/actors/")
    suspend fun getActors(
        @Query("search") search: String? = null,
        @Query("page") page: Int = 1
    ): ActorsResponse
    
    @GET("api/actors/{id}/")
    suspend fun getActorDetails(@Path("id") actorId: Int): ActorDetailDto
    
    @GET("api/actors/{id}/movies/")
    suspend fun getActorMovies(@Path("id") actorId: Int): MoviesResponse
}
