package br.pedroso.upcomingMovies.rest;

import br.pedroso.upcomingMovies.rest.model.MovieResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by felip on 20/07/2016.
*/
public interface MoviesService {
    final String BASE_URL = "http://api.themoviedb.org/3/";

    @GET("movie/upcoming")
    Call<MovieResults> listUpcomingMovies();

    @GET("movie/{movieId}/similar")
    Call<MovieResults> listSimilarMovies(@Path("movieId") Integer movieId);
}
