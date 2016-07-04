package ie.ait.stephen.recmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import ie.ait.stephen.recmoviesapp.model.DatabaseHandler;
import ie.ait.stephen.recmoviesapp.model.Movie;

public class QueryGenres extends AppCompatActivity {

    EditText genreText;
    static ArrayList<Movie> movies;

    public DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_genres);

        genreText = (EditText) findViewById(R.id.enterGenres);
    }

    public void onButtonClick(View v)
    {
        System.out.println("onButtonClick");
        if (v.getId() == R.id.bGenres) {
            System.out.println("Inside IFFF");
            movies = db.getMoviesByGenre(genreText.getText().toString());
            Log.d("Length of movies list", "" + movies.size());

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("movieList", movies);

            Intent listMoviesIntent = new Intent(QueryGenres.this, ListMoviesActivity.class);

            listMoviesIntent.putExtras(bundle);
            startActivity(listMoviesIntent);
            Log.d("List_of_movies intent", "Genre query");
        }
    }
}
