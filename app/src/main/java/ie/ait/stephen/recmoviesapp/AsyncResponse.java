package ie.ait.stephen.recmoviesapp;

import java.util.List;

import ie.ait.stephen.recmoviesapp.model.Movie;

/**
 * Created by stephen on 3/27/2016.
 */
public interface AsyncResponse {
    void processFinish(List<Movie> movieList);
}
