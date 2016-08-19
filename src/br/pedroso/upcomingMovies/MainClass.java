/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pedroso.upcomingMovies;

import br.pedroso.upcomingMovies.rest.ApiKeyInterceptor;
import br.pedroso.upcomingMovies.rest.MoviesService;
import br.pedroso.upcomingMovies.rest.model.MovieInfo;
import br.pedroso.upcomingMovies.rest.model.MovieResults;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author felip
 */
public class MainClass extends Application implements Callback<MovieResults> {

    private TilePane movieTiles;
    private final double MOVIE_POSTER_WIDTH = 150;
    private final double MOVIE_POSTER_HEIGHT = 200;

    @Override
    public void start(Stage primaryStage) {
        movieTiles = new TilePane(Orientation.HORIZONTAL, 5, 10);
        movieTiles.setAlignment(Pos.CENTER);
        movieTiles.setTileAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(movieTiles);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        loadMoviePosters();

        Scene scene = new Scene(scrollPane, 640, 480);

        primaryStage.setTitle("Upcoming Movies");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadMoviePosters() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(new ApiKeyInterceptor("<YOUR-API-KEY>"));
        OkHttpClient httpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MoviesService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        MoviesService moviesService = retrofit.create(MoviesService.class);

        moviesService.listUpcomingMovies().enqueue(this);
    }

    @Override
    public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateMovieTiles(response.body());
            }
        });
    }

    @Override
    public void onFailure(Call<MovieResults> call, Throwable thrwbl) {
    }

    private void updateMovieTiles(MovieResults results) {
        if (results == null || results.getResults().isEmpty()) {
            // TODO: show no items found
            return;
        }

        for (MovieInfo movieInfo : results.getResults()) {
            Node movieInfoTile = createMovieInfoTile(movieInfo);

            if (movieInfoTile != null) {

                movieTiles.getChildren().add(movieInfoTile);
            }
        }
    }

    private Node createMovieInfoTile(MovieInfo movieInfo) {
        String moviePosterPath = "http://image.tmdb.org/t/p/w500" + movieInfo.getPosterPath();
        Image moviePosterImage = new Image(moviePosterPath, MOVIE_POSTER_WIDTH, MOVIE_POSTER_HEIGHT, true, true, false);

        if (moviePosterImage.getWidth() == 0 && moviePosterImage.getHeight() == 0) {
            return null;
        }

        ImageView imageView = new ImageView(moviePosterImage);
        Tooltip.install(imageView, new Tooltip(movieInfo.getTitle()));

        return imageView;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
