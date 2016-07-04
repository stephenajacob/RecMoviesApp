package ie.ait.stephen.recmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by stephen on 1/16/2016.
 */
public class Movie implements Comparator<Movie>, Parcelable, Serializable {

    private int id;
    private int movieId;
    private String title;
    private String genres;
    private int rating;


    public Movie(int id, int movieId, String title, String genres) {
        this.id = id;
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }

    public Movie(int movieId, String title, String genres, int rating) {
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
        this.rating = rating;
    }

    public Movie() {
    }

    public int getId() {
        return id;
    }
    public int getMovieId() {
        return movieId;
    }
    public String getTitle() {
        return title;
    }
    public String getGenres() {
        return genres;
    }
    public int getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenres(String genres) { this.genres = genres; }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(genres);
        dest.writeInt(rating);
    }

    public void readFromParcel(Parcel in){
        id = in.readInt();
        movieId = in.readInt();
        title = in.readString();
        genres = in.readString();
        rating = in.readInt();
    }

    public Movie(Parcel in) {
        this();
        readFromParcel(in);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size){
            return new Movie[size];
        }

    };

    public int compare(Movie movie1, Movie movie2){

        if(movie1.getTitle() != movie2.getTitle()){
            return 1;
        } else {
            return -1;
        }
    }


}
