package com.example.faruk.loodosmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.faruk.loodosmovies.adapter.MyAdapter;
import com.example.faruk.loodosmovies.api.MovieService;
import com.example.faruk.loodosmovies.api.RetrofitClient;
import com.example.faruk.loodosmovies.helper.Common;
import com.example.faruk.loodosmovies.helper.OnItemClickListener;
import com.example.faruk.loodosmovies.models.Movies;
import com.example.faruk.loodosmovies.models.Search;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {
    EditText etSearch;
    Button btnSearch;
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Search> searchArrayList;
    RetrofitClient client;
    MovieService service;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initViews();
        adapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v , int position) {
                Intent intent=new Intent(getApplicationContext(),MovieDetailActivity.class);
                intent.putExtra("Title",searchArrayList.get(position).getTitle());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=etSearch.getText().toString().trim();
                if (!text.equals("")) {
                    Call<Movies> call = service.getSearchedMovies(text,RetrofitClient.APIKEY);
                    call.enqueue(new Callback<Movies>() {
                        @Override
                        public void onResponse(Call<Movies> call, Response<Movies> response) {
                            showDialog();
                            searchArrayList=response.body().getSearch();
                            if(searchArrayList!=null){
                                hideKeyboard();
                                adapter.searchList=searchArrayList;
                                adapter.notifyDataSetChanged();
                            }
                            else
                                Toast.makeText(MoviesActivity.this, "The Movie Could Not Be Found.Try again!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        @Override
                        public void onFailure(Call<Movies> call, Throwable t) {
                            Toast.makeText(MoviesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
                else
                    Toast.makeText(MoviesActivity.this, "Please Enter The Movie", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void hideKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void showDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Loodos");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void initViews() {
        searchArrayList = new ArrayList<>();
        adapter = new MyAdapter(this,searchArrayList);
        etSearch=(EditText)findViewById(R.id.etSearch);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        client=new RetrofitClient();
        service=client.getClient().create(MovieService.class);
    }
}
