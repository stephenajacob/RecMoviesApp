package ie.ait.stephen.recmoviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ie.ait.stephen.recmoviesapp.model.DatabaseHandler;
import ie.ait.stephen.recmoviesapp.model.Movie;
import ie.ait.stephen.recmoviesapp.http.MyHttpConnection;

public class RecommendMovie extends AppCompatActivity implements AsyncResponse {

    public DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Recommend activity stated");
        setContentView(R.layout.activity_recommend__movie);

        String url = "https://public.opencpu.org/ocpu/github/stephenajacob/" +
                "recommendMovies/R/getRecommendation/json" ;
        String jsonString = getRatingsAsJSONString();
        PostTask task = new PostTask(this);
        task.execute(url, jsonString);

//        try {
//            task.get(5000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.recommendMoviesReturn) {
            System.out.println("Click: return to main page");
            Intent returnIntent = new Intent(RecommendMovie.this, MainActivity.class);
            startActivity(returnIntent);
        }
    }

    // this overrides the implemented method from asyncTask
    public void processFinish(List<Movie> recommendedMovies){
        ListView listView = (ListView)findViewById(R.id.recommend_list_view);
        ArrayAdapter<Movie> adapter = new InteractiveArrayAdapter(this, recommendedMovies);
        listView.setAdapter(adapter) ;
    }


    private String getRatingsAsJSONString(){
        List<Movie> movies = db.getRatedMovies();

        List<Movie> newMovies = new ArrayList<Movie>();
        for(int i = 0; i < movies.size()-1; i++){
            newMovies.add(movies.get(i));
        }

        String moviesJSONData = "{\"movies\": [";
        for(Movie mID: newMovies){
            moviesJSONData += mID.getMovieId() + ", ";
        }
        moviesJSONData += movies.get(movies.size()-1).getMovieId();
        moviesJSONData += "], \"ratings\": [";
        for(Movie mRating: newMovies) {
            moviesJSONData += mRating.getRating() + ", ";
        }
        moviesJSONData += movies.get(movies.size()-1).getRating();
        moviesJSONData += "]}";

        System.out.println("JSON Request: " + moviesJSONData);
        return moviesJSONData;
    }

    private class PostTask extends AsyncTask<String, String, String> {
        public AsyncResponse delegate = null;

        public PostTask(AsyncResponse delegate){
            this.delegate = delegate;
        }

        @Override
        protected String doInBackground(String... params) {
                String data = "";
            try {
                MyHttpConnection myHttpConnection = new MyHttpConnection();
                data = myHttpConnection.postToUrl(params[0], params[1]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            System.out.println("data: " + data);
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Result onPostExecute" + result);
            int start = result.lastIndexOf("[") + 1 ;
            int end = result.indexOf("]") - 1 ;

            List<Integer> movieIds = new ArrayList<Integer>();
            if(end >= start) {
                String s = result.substring(start, end);
                System.out.println(s);

                String[] sa = s.split(",");

                for (int i = 0; i < sa.length; i++) {
                    movieIds.add(Integer.parseInt(sa[i].trim()));
                    System.out.println(movieIds.get(i));
                }
            }
            List<Movie> recommendedMovies = db.getMoviesById(movieIds);
            delegate.processFinish(recommendedMovies);
        }
    }
}