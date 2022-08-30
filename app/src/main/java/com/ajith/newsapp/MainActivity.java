package com.ajith.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {


//    b93c421b79134a7e9fca4ba51d186d5f

    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Article> articleArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV=findViewById(R.id.idRVNews);
        categoryRV=findViewById(R.id.idRVCategories);
        loadingPB=findViewById(R.id.idPBLoading);

        articleArrayList= new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter= new NewsRVAdapter(articleArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

//        getCategories();
        getNews(    "All");
        newsRVAdapter.notifyDataSetChanged();
    }
    private void getCategories()
    {
         categoryRVModalArrayList.add( new CategoryRVModal("All","https://images.unsplash.com/photo-1529753253655-470be9a42781?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YWxsJTIwYmxhY2t8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add( new CategoryRVModal("Technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add( new CategoryRVModal("Science","https://images.unsplash.com/photo-1532094349884-543bc11b234d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8c2NpZW5jZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add( new CategoryRVModal("General","https://images.unsplash.com/photo-1432821596592-e2c18b78144f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add( new CategoryRVModal("Sports","https://images.unsplash.com/photo-1459865264687-595d652de67e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8c3BvcnRzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add( new CategoryRVModal("Bussiness","https://images.unsplash.com/photo-1557804506-669a67965ba0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8YnVzc2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add( new CategoryRVModal("Entertainment","https://images.unsplash.com/photo-1499364615650-ec38552f4f34?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add( new CategoryRVModal("Health","https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));

        categoryRVAdapter.notifyDataSetChanged();
    }


    private void  getNews(String category)
    {
        loadingPB.setVisibility(View.VISIBLE);
        articleArrayList.clear();
        String categoryURL =" https://newsapi.org/v2/top-headlines/sources?&apiKey=b93c421b79134a7e9fca4ba51d186d5f";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=b93c421b79134a7e9fca4ba51d186d5f";

        String BASE_URL="https://newsapi.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call=retrofitAPI.getAllNews(url);
        }
        else{
            call= retrofitAPI.getNewsByCategory(categoryURL);
        }


        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {

                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Article>articles = newsModal.getArticles() ;
                for(int i=0;i<articles.size() ;i++){
                    articleArrayList.add(
                            new Article(articles.get(i).getTitle(),  articles.get(i).getDescription(),
                                       articles.get(i).getUrlToImage(),   articles.get(i).getUrl(),articles.get(i).getContent())
                    );
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Fail to get news " + t.toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onCategoryClick(int position) {

        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}