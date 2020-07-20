package com.sajal.retrofittutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        //To force null values in patch request
        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//        getPosts();
//        getComments();
//        createPosts();
//        updatePost();
        deletePost();
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: "+response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(12, null, "New Text");
        Call<Post> call = jsonPlaceHolderApi.putPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: z" + response.code());
                }

                Post post1 = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + post1.getId() + "\n";
                content += "User ID: " + post1.getUserid() + "\n";
                content += "Title: " + post1.getTitle() + "\n";
                content += "Text: " + post1.getText() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPosts() {

        Post post = new Post(23, "New Title", "New text");

        Map<String, String> params = new HashMap<>();
        params.put("userId", "23");
        params.put("title", "New Title");

        Call<Post> call = jsonPlaceHolderApi.createPost(params);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                }

                Post post1 = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + post1.getId() + "\n";
                content += "User ID: " + post1.getUserid() + "\n";
                content += "Title: " + post1.getTitle() + "\n";
                content += "Text: " + post1.getText() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

//        Call<List<Post>> call = jsonPlaceHolderApi.createPost(new Integer[]{2, 3, 4}, "New Title", "New Text");
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if (!response.isSuccessful()) {
//                    textViewResult.setText("Code: " + response.code());
//                }
//
//                List<Post> posts = response.body();
//                for (Post post : posts
//                ) {
//                    String content = "";
//                    content += "Code: " + response.code() + "\n";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserid() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " + post.getText() + "\n";
//
//                    textViewResult.append(content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });
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
                            comment.getName() + "\nEmail: " + comment.getEmail() + "\nText: " + comment.getText() + "\n\n";

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
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(5);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "2");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
//        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);

//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if (!response.isSuccessful()) {
//                    textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//
//                List<Post> posts = response.body();
//
//                for (Post post : posts
//                ) {
//                    String content = "";
//                    content += "User ID: " + post.getUserid() + "\nID: " + post.getId() + "\n" + "Title: " + post.getTitle() + "\nText: " + post.getText() + "\n\n";
//                    textViewResult.append(content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//            }
//        });

        Call<Post> call = jsonPlaceHolderApi.getPosts(5);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                String content = "";
                Post post = response.body();
                content += "User ID: " + post.getUserid() + "\nID: " + post.getId() + "\n" + "Title: " + post.getTitle() + "\nText: " + post.getText() + "\n\n";
                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}