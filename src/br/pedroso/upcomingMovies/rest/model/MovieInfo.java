package br.pedroso.upcomingMovies.rest.model;

/**
 * Created by felipe on 20/07/2016.
 */
public class MovieInfo {
    private Integer id;
    private String poster_path;
    private String title;
    public Double vote_average;
    public String release_date;
    public String overview;

    public MovieInfo(Integer id, String poster_path, String title, Double vote_average, String release_date, String overview) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.overview = overview;
    }

    public Integer getId() {
        return id;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return release_date;
    }
}
