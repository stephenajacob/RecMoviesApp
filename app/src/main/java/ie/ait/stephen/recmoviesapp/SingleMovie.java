package ie.ait.stephen.recmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import ie.ait.stephen.recmoviesapp.model.DatabaseHandler;
import ie.ait.stephen.recmoviesapp.model.Movie;

public class SingleMovie extends AppCompatActivity {

    EditText movieTitle;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        movieTitle = (EditText) findViewById(R.id.enterText);
    }

    public void onButtonClick(View v)
    {
        System.out.println("onButtonClick");
        if (v.getId() == R.id.querySingleMovie)
        {
            System.out.println("inside if");
            Movie movie ;
            movie = db.getMovie(movieTitle.getText().toString());
            if (movie.getTitle().equals("Not found")) {
                movie = new Movie(1000, "Troy adventure", "Comedy", 0);
            }

            //movie = new Movie(1000, "Troy adventure", "Comedy");
            System.out.println("Movie from db" + movie.getTitle());
            Bundle bundle = new Bundle();

            Intent singleMovieIntent = new Intent(SingleMovie.this, ShowMovieData.class);
            bundle.putSerializable("A single movie", movie);

            singleMovieIntent.putExtras(bundle);
            startActivity(singleMovieIntent);
            System.out.println("Movie from db");
        }
    }
}
