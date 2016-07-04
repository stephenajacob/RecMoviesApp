package ie.ait.stephen.recmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ie.ait.stephen.recmoviesapp.model.Movie;

public class ListMoviesActivity extends AppCompatActivity {

    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);

        Bundle bundle = getIntent().getExtras();
        movieList = bundle.getParcelableArrayList("movieList");

        Log.d("MovieList", "Got movieList from parcel " + movieList.size());
        ArrayAdapter<Movie> adapter = new InteractiveArrayAdapter(this, movieList);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    public void onButtonClick(View v) {
        if(v.getId() == R.id.listMoviesReturn) {
            System.out.println("Click: return to main page");
            Intent returnIntent = new Intent(ListMoviesActivity.this, MainActivity.class);
            startActivity(returnIntent);
        }
    }
}

