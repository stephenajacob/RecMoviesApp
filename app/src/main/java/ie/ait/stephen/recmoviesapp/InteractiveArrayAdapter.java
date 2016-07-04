package ie.ait.stephen.recmoviesapp;

/**
 * Created by stephen on 3/1/2016.
 */
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import ie.ait.stephen.recmoviesapp.model.DatabaseHandler;
import ie.ait.stephen.recmoviesapp.model.Movie;

public class InteractiveArrayAdapter extends ArrayAdapter<Movie> {

    private final List<Movie> movieList;
    private final Activity context;
    DatabaseHandler db ;

    public InteractiveArrayAdapter(Activity context, List<Movie> movieList) {
        super(context, R.layout.activity_list_movies, R.id.label, movieList);
        db = new DatabaseHandler(context);
        this.context = context;
        this.movieList = movieList;
    }

    static class ViewHolder {
        protected TextView ratingText;
        protected RatingBar ratingBar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("InterActiveAdapter", "getView() " + movieList.size());
        View rowView = null;
        if (convertView == null) {
            Log.d("getView: ", "INSIDE IF") ;
            LayoutInflater inflator = context.getLayoutInflater();
            rowView = inflator.inflate(R.layout.content_row_layout, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.ratingText = (TextView) rowView.findViewById(R.id.label);
            viewHolder.ratingBar = (RatingBar) rowView.findViewById(R.id.rating);
            viewHolder.ratingBar
                    .setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            Movie movie = (Movie) viewHolder.ratingBar.getTag();
                            movie.setRating((int)ratingBar.getRating());
                            db.setMovieRating(movie);
                        }
                    });
            rowView.setTag(viewHolder);
            viewHolder.ratingBar.setTag(movieList.get(position));
        } else {
            rowView = convertView;
            ((ViewHolder) rowView.getTag()).ratingBar.setTag(movieList.get(position));
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Log.d("getView: list size ", "" + movieList.size()) ;
        Log.d("getView: position", "" + position) ;
        Log.d("getView: ", movieList.get(position).getTitle()) ;
        Log.d("getView: rating", "" + movieList.get(position).getRating());
        holder.ratingText.setText(movieList.get(position).getTitle());
        holder.ratingBar.setRating(movieList.get(position).getRating());
        return rowView;
    }
}
