package com.example.tutorial10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityHome extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    List<User> users;
    private static String JSON_URL = "https://jsonplaceholder.typicode.com/users";
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        preferences = getSharedPreferences("MyShPre",MODE_PRIVATE);
        editor = preferences.edit();

        Intent i= getIntent();
        String s = i.getStringExtra("uname");
        Toast.makeText(getApplicationContext(), "User Login : " + s, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.userList);
        users = new ArrayList<>();
        extractUsers();


    }

    private void extractUsers() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject userObject = response.getJSONObject(i);

                        User user = new User();
                        user.setName(userObject.getString("name"));
                        user.setUsername(userObject.getString("username"));
                        user.setEmail(userObject.getString("email"));

                        users.add(user);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(),users);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse :" + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.Logout:
                userLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void userLogout() {
        editor.remove("Login");
        editor.commit();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"User Not Login",Toast.LENGTH_SHORT).show();
    }
}