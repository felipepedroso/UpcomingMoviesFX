package br.pedroso.upcomingMovies.rest.model;

import java.util.List;

/**
 * Created by felip on 22/07/2016.
 */
public class MovieResults {
    private List<MovieInfo> results;

    public MovieResults(List<MovieInfo> results) {
        this.results = results;
    }

    public List<MovieInfo> getResults() {
        return results;
    }
}
