package ie.ait.stephen.recmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ie.ait.stephen.recmoviesapp.model.Movie;

public class RatedMovieList extends AppCompatActivity {

    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rated_movie_list);

        Bundle bundle = getIntent().getExtras();
        movieList = bundle.getParcelableArrayList("ratedMovieList");

//        System.out.println("Rated|Movie Lists: **************************" + movieList.get(0).getRating());
//        System.out.println("Rated|Movie Lists: **************************" + movieList.get(0).getTitle());

        ArrayAdapter<Movie> adapter = new InteractiveArrayAdapter(this, movieList);

        ListView listView = (ListView)findViewById(R.id.rated_list_view);
        listView.setAdapter(adapter) ;
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.ratedMoviesReturn) {
            System.out.println("Click: return to main page");
            Intent returnIntent = new Intent(RatedMovieList.this, MainActivity.class);
            startActivity(returnIntent);
        }
    }
}
