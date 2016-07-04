package ie.ait.stephen.recmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ie.ait.stephen.recmoviesapp.model.Movie;

public class ShowMovieData extends AppCompatActivity {

    TextView movieId, movieTitle, movieGenres;
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_data);

        Intent input = getIntent();
        Bundle bundle = input.getExtras();
        movie = (Movie) bundle.getSerializable("A single movie");
        System.out.println("Movie data serialized");

        movieTitle = (TextView) findViewById(R.id.showMovieTitle);
        movieId = (TextView) findViewById(R.id.showMovieId);
        movieGenres = (TextView) findViewById(R.id.showMovieGenres);

        movieTitle.setText("Title of movie: " + movie.getTitle());
        movieGenres.setText("Genres present in this movie: " + movie.getGenres());
        movieId.setText("Registered identification: " + movie.getMovieId());

    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.movieDataReturn) {
            System.out.println("Click: return to main page");
            Intent returnIntent = new Intent(ShowMovieData.this, MainActivity.class);
            startActivity(returnIntent);
        }
    }

}
