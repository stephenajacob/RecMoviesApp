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

public class QueryTitles extends AppCompatActivity {

    EditText queryText;
    static ArrayList<Movie> movies;

    public DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_titles);

        queryText = (EditText) findViewById(R.id.movieTitleText);
    }

    public void onButtonClick(View v)
    {
        System.out.println("onButtonClick");
        if (v.getId() == R.id.bText) {

            System.out.println(queryText.getText().toString());
            movies = db.getMovieListByTitle(queryText.getText().toString());

            Log.d("Movies returned ", "" + movies.size());

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("movieList", movies);

            Intent listMoviesIntent = new Intent(QueryTitles.this, ListMoviesActivity.class);

            listMoviesIntent.putExtras(bundle);
            startActivity(listMoviesIntent);
            Log.d("QueryTitles: ", "Started list_of_movies intent");
        }
    }
}
