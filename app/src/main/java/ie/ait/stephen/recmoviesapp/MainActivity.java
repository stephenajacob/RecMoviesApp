package ie.ait.stephen.recmoviesapp;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ie.ait.stephen.recmoviesapp.model.DatabaseHandler;
import ie.ait.stephen.recmoviesapp.model.Movie;

public class MainActivity extends AppCompatActivity {

    public DatabaseHandler db = new DatabaseHandler(this);
    Button rcdButton;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        spinner=(ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        // saveMovies() ;
        // printMovies() ;

    }

    @Override
    public void onStop(){
        super.onStop();
        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View v) {
        System.out.println("Click");
//        if (v.getId() == R.id.NewMovie) {
//            System.out.println("Click: single movie");
//            Intent i = new Intent(MainActivity.this, SingleMovie.class);
//            System.out.println("single movie intent" + i);
//            startActivity(i);
//        }

        if (v.getId() == R.id.BlistOfMovies) {
            System.out.println("Click: query by title");
            Intent movieListIntent = new Intent(MainActivity.this, QueryTitles.class);
            System.out.println("queryTitleIntent" + movieListIntent);
            startActivity(movieListIntent);
        }

        if (v.getId() == R.id.BQueryMovieGenres){
            System.out.println("Click: query by genre");
            Intent movieGenreIntent = new Intent(MainActivity.this, QueryGenres.class);
            System.out.println("queryGenreIntent started" + movieGenreIntent);
            startActivity(movieGenreIntent);
        }

        if (v.getId() == R.id.BRated){
            ArrayList<Movie> movies = db.getRatedMovies();
            System.out.println("Click: rated movies");
            //System.out.println("Main Activity: **************************" + movies.get(0).getRating());

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("ratedMovieList", movies);

            Intent ratedMoviesIntent = new Intent(MainActivity.this, RatedMovieList.class);
            ratedMoviesIntent.putExtras(bundle);

            System.out.println("ratedIntent" + ratedMoviesIntent);
            startActivity(ratedMoviesIntent);
        }

        if(v.getId() == R.id.BRcmder){
            System.out.println("Click: recommend movies");
            spinner.setVisibility(View.VISIBLE);
            Intent recommendMovieIntent = new Intent(MainActivity.this, RecommendMovie.class);
            startActivity(recommendMovieIntent);
            System.out.println("Recommended movies intent started");
        }

        if(v.getId() == R.id.BClear){
            System.out.println("Click: clear movie ratings");
            db.clearRatings();
//            Intent clearMovieIntent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(clearMovieIntent);
            System.out.println("Clear ratings intent started");
        }

//        if (v.getId() == R.id.Bcancel) {
//            finish();
//        }
    }

    private void saveMovies() {
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            InputStream input = getAssets().open("movies.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String inputLine = reader.readLine();

            while ((inputLine = reader.readLine()) != null) {
                //System.out.println("Input: " + inputLine);
                String[] array = inputLine.split(",");
                int movieId = Integer.parseInt(array[0]);
                String title = array[1];
                String genres = array[2];
                Movie newMovie = new Movie(movieId, title, genres, 0);
                db.addMovie(newMovie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printMovies() {

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("Reading: ", "Reading all movies.");
        Log.d("MainActivity ", "onCreate()");
        List<Movie> movies = db.getAllMovies();

        for (Movie movie : movies) {
            String log = "Id: " + movie.getId() + ", movieId: " + movie.getMovieId() + ", Title: " +
                    movie.getTitle() + ", Genres: " + movie.getGenres();
            // Writing movies to log
            Log.d("Name: ", log);
        }
        Log.d("oncreate()", "");
        System.out.println("oncreate()");
        Log.d("LLLLLLDDDDDDDD", "");
    }
}
