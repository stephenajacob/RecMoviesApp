package ie.ait.stephen.recmoviesapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stephen on 1/16/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MOVIEDB" ;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Creating table", "");
        String CREATE_TABLE = "CREATE TABLE MOVIES (ID INTEGER PRIMARY KEY, MOVIE_ID INTEGER, " +
                "MOVIE_TITLE TEXT, MOVIE_GENRES TEXT, MOVIE_RATING INTEGER)" ;
        db.execSQL(CREATE_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS MOVIES ");

        // Create tables again
        onCreate(db);
    }

    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("MOVIE_ID", movie.getMovieId());
        values.put("MOVIE_TITLE", movie.getTitle());
        values.put("MOVIE_GENRES", movie.getGenres());
        values.put("MOVIE_RATING", 0);

        // Inserting Row
        db.insert("MOVIES", null, values);
        db.close(); // Closing database connection
    }

    public Movie getMovie(String movieTitle){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM MOVIES WHERE MOVIE_TITLE LIKE '%" + movieTitle + "%'";

        Movie movie = new Movie();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            movie.setId(Integer.parseInt(cursor.getString(0)));
            movie.setMovieId(Integer.parseInt(cursor.getString(1)));
            movie.setTitle(cursor.getString(2));
            movie.setGenres(cursor.getString(3));
        } else {
            movie.setTitle("Not found");
        }
        return movie;
    }

    // retrieving a list of movies, by searching titles
    public ArrayList<Movie> getMovieListByTitle(String movieText){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Movie> movieList = new ArrayList<Movie>();

        String searchQuery = "SELECT * FROM MOVIES WHERE MOVIE_TITLE LIKE '%" + movieText + "%'";
        Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setMovieId(Integer.parseInt(cursor.getString(1)));
                movie.setTitle(cursor.getString(2));
                movie.setGenres(cursor.getString(3));
                movie.setRating(Integer.parseInt(cursor.getString(4)));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        return movieList;
    }

    public ArrayList<Movie> getMoviesById(List<Integer> movieIds){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Movie> movies = new ArrayList<Movie>();

        Cursor cursor;
        for(int id: movieIds) {
            String idQuery = "SELECT * FROM MOVIES WHERE MOVIE_ID == " + id;
            cursor = db.rawQuery(idQuery, null);

            if (cursor.moveToFirst()) {

                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setMovieId(Integer.parseInt(cursor.getString(1)));
                movie.setTitle(cursor.getString(2));
                movie.setGenres(cursor.getString(3));
                movie.setRating(Integer.parseInt(cursor.getString(4)));
                movies.add(movie);
            }
        }
        return movies;
    }

    public ArrayList<Movie> getMoviesByGenre(String genre){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Movie> movieList = new ArrayList<Movie>();

        String genresQuery = "SELECT * FROM MOVIES WHERE MOVIE_GENRES LIKE '%" + genre + "%'";
        Cursor cursor = db.rawQuery(genresQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setMovieId(Integer.parseInt(cursor.getString(1)));
                movie.setTitle(cursor.getString(2));
                movie.setGenres(cursor.getString(3));
                movie.setRating(Integer.parseInt(cursor.getString(4)));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        return movieList;
    }

    // Getting All Contacts
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT * FROM MOVIES" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setMovieId(Integer.parseInt(cursor.getString(1)));
                movie.setTitle(cursor.getString(2));
                movie.setGenres(cursor.getString(3));
                movie.setRating(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        // return contact list
        return movieList;
    }

    public ArrayList<Movie> getRatedMovies(){
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        String selectQuery = "SELECT * FROM MOVIES WHERE MOVIE_RATING <> 0 ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setMovieId(Integer.parseInt(cursor.getString(1)));
                movie.setTitle(cursor.getString(2));
                movie.setGenres(cursor.getString(3));
                movie.setRating(Integer.parseInt(cursor.getString(4)));
                System.out.println("Rating: **************************" + movie.getRating());
                // Adding contact to list
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        // return contact list
        return movieList;
    }

    public void clearRatings(){
        String updateQuery = "UPDATE MOVIES SET MOVIE_RATING = 0 WHERE MOVIE_RATING <> 0";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(updateQuery);
    }

    public void setMovieRating(Movie movie){
        String updateString = "UPDATE MOVIES SET MOVIE_RATING = " + movie.getRating() +
                " WHERE MOVIE_ID = " + movie.getMovieId();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(updateString);
    }

    public void dropTable(){
        Log.d("Dropping table","");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS MOVIES ");

        onCreate(db) ;
    }
}
