package com.example.faruk.loodosmovies;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faruk.loodosmovies.adapter.CustomListAdapter;
import com.example.faruk.loodosmovies.api.MovieService;
import com.example.faruk.loodosmovies.api.RetrofitClient;
import com.example.faruk.loodosmovies.models.Movie;
import com.example.faruk.loodosmovies.models.Rating;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvMoviePlot,tvMovieImdbRating,tvMovieAwards,tvMovieCountry,tvMovieLanguage,tvMovieMetaScore,tvMovieImdbVotes,tvMovieType;
    TextView tvMovieTitle,tvMovieYear,tvMovieRated,tvMovieReleased,tvMovieRuntime,tvMovieGenre,tvMovieDirector,tvMovieWriter,tvMovieActors;
    TextView tvMovieDvd,tvMovieBox,tvMovieProduction,tvMovieWebsite;
    ImageView ivMoviePoster;
    Toolbar toolbar;
    RetrofitClient client;
    MovieService service;
    Bundle extras;
    ProgressDialog progressDialog;
    FirebaseAnalytics firebaseAnalytics;
    ArrayList<Rating> ratingArrayList;
    ListView lvRatings;
    CustomListAdapter listViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        extras=getIntent().getExtras();
        String title = extras.getString("Title");
        client=new RetrofitClient();
        service=client.getClient().create(MovieService.class);
        Call<Movie> call=service.getMovieDetail(title,RetrofitClient.APIKEY);
        showDialog();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                setViews(response.body(),(ArrayList<Rating>) response.body().getRatings());
                sendLog();
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private void sendLog()
    {
    Bundle params = new Bundle();
    params.putString("director",tvMovieDirector.getText().toString());
    params.putString("genre",tvMovieGenre.getText().toString());
    params.putString("imdbRating",tvMovieImdbRating.getText().toString());
    params.putString("year",tvMovieYear.getText().toString());
    params.putString("title",tvMovieTitle.getText().toString());
    params.putString("writer",tvMovieWriter.getText().toString());
    params.putString("actors",tvMovieActors.getText().toString());
    params.putString("awards",tvMovieAwards.getText().toString());
    params.putString("language",tvMovieLanguage.getText().toString());
    params.putString("country",tvMovieCountry.getText().toString());
    firebaseAnalytics.logEvent("movie", params);
    }
    private void setViews(Movie movie,ArrayList<Rating> ratings){
        ratingArrayList = new ArrayList<Rating>();
        listViewAdapter = new CustomListAdapter(MovieDetailActivity.this,ratings);
        lvRatings.setAdapter(listViewAdapter);
        tvMovieImdbRating.setText("IMDB : "+movie.getImdbRating());
        tvMovieDirector.setText("Director : "+movie.getDirector());
        tvMovieGenre.setText("Genre : "+movie.getGenre());
        tvMovieYear.setText("Year : "+movie.getYear());
        tvMovieTitle.setText(movie.getTitle());
        tvMovieActors.setText("Actors : "+movie.getActors());
        tvMovieWriter.setText("Writer : "+movie.getWriter());
        tvMovieAwards.setText("Awards : "+movie.getAwards());
        tvMovieLanguage.setText("Language : "+movie.getLanguage());
        tvMovieCountry.setText("Country : "+movie.getCountry());
        tvMoviePlot.setText("Plot\n\n"+movie.getPlot());
        tvMovieMetaScore.setText("MetaScore : "+movie.getMetascore());
        tvMovieImdbVotes.setText("Votes : "+movie.getImdbVotes());
        tvMovieType.setText("Type : "+movie.getType());
        tvMovieRated.setText("Rated : "+movie.getRated());
        tvMovieReleased.setText("Released : "+movie.getReleased());
        tvMovieRuntime.setText("Runtime : "+movie.getRuntime());
        tvMovieDvd.setText("DVD : "+movie.getDVD());
        tvMovieBox.setText("BoxOffice : "+movie.getBoxOffice());
        tvMovieProduction.setText("Production : "+movie.getProduction());
        tvMovieWebsite.setText("Website : "+movie.getWebsite());
        Picasso.with(this).load(movie.getPoster()).resize(500, 500).into(ivMoviePoster);
        toolbar.setTitle(movie.getTitle());
    }

    private void initViews()
    {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        lvRatings= (ListView) findViewById(R.id.lvRatings);
        tvMoviePlot=(TextView)findViewById(R.id.tvMoviePlot);
        tvMovieDirector=(TextView)findViewById(R.id.tvMovieDirector);
        tvMovieGenre=(TextView)findViewById(R.id.tvMovieGenre);
        tvMovieYear=(TextView)findViewById(R.id.tvMovieYear);
        tvMovieTitle=(TextView)findViewById(R.id.tvMovieTitle);
        tvMovieImdbRating=(TextView)findViewById(R.id.tvMovieImdbRating);
        tvMovieActors=(TextView)findViewById(R.id.tvMovieActors);
        tvMovieAwards=(TextView)findViewById(R.id.tvMovieAwards);
        tvMovieCountry=(TextView)findViewById(R.id.tvMovieCountry);
        tvMovieLanguage=(TextView)findViewById(R.id.tvMovieLanguage);
        tvMovieWriter=(TextView)findViewById(R.id.tvMovieWriter);
        tvMovieMetaScore=(TextView)findViewById(R.id.tvMovieMetaScore);
        tvMovieImdbVotes=(TextView)findViewById(R.id.tvMovieImdbVotes);
        tvMovieType=(TextView)findViewById(R.id.tvMovieType);
        tvMovieRated=(TextView)findViewById(R.id.tvMovieRated);
        tvMovieReleased=(TextView)findViewById(R.id.tvMovieReleased);
        tvMovieRuntime=(TextView)findViewById(R.id.tvMovieRuntime);
        tvMovieRated=(TextView)findViewById(R.id.tvMovieRated);
        tvMovieDvd=(TextView)findViewById(R.id.tvMovieDvd);
        tvMovieBox=(TextView)findViewById(R.id.tvMovieBox);
        tvMovieProduction=(TextView)findViewById(R.id.tvMovieProduction);
        tvMovieWebsite=(TextView)findViewById(R.id.tvMovieWebsite);
        ivMoviePoster=(ImageView) findViewById(R.id.ivMoviePoster);
    }
    private void showDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setTitle("Loodos");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
}
