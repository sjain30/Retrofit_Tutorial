package com.sajal.retrofittutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_veiew_result);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//        getPosts();
        getComments();
    }

    private void getComments() {
//        Call<List<Comment>> comments = jsonPlaceHolderApi.getComments(3);

        Call<List<Comment>> comments = jsonPlaceHolderApi.getComments("post/3/comments");
        comments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                }

                List<Comment> comments1 = response.body();

                for (Comment comment : comments1
                ) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\nPostId: " + comment.getPostId() + "\nName: " +
                            comment.getName() + "\nEmail: " + comment.getEmail() + "\nText: " + comment.getText()+"\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
//        Call<List<Post>> call = jsonPlaceHolderApi.getPost(new Integer[]{2,3,4},null,null);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "2");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts
                ) {
                    String content = "";
                    content += "User ID: " + post.getUserid() + "\nID: " + post.getId() + "\n" + "Title: " + post.getTitle() + "\nText: " + post.getText() + "\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}