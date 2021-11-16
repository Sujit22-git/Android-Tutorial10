package com.example.tutorial10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText uname,pass;
    Button login;

    DBHelper DB;
    String u,p;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = findViewById(R.id.txtluname);
        pass = findViewById(R.id.txtlpass);
        login = findViewById(R.id.btnlogin);
        DB = new DBHelper(this);

        preferences = getSharedPreferences("MyShPre",MODE_PRIVATE);
        editor = preferences.edit();
        String s = preferences.getString("Login",null);

        if(s != null){
            Intent i = new Intent(MainActivity.this, MainActivityHome.class);
            i.putExtra("uname",s);
            startActivity(i);
            finish();
            Toast.makeText(getApplicationContext(),"Username : "+String.valueOf(s) ,Toast.LENGTH_SHORT).show();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = uname.getText().toString().trim();
                p = pass.getText().toString().trim();

                if(checkUserLogin(u,p)){
                    boolean checkuserpass = DB.checkusernamepassword(u,p);
                    if(checkuserpass == true){
                        Toast.makeText(getApplicationContext(),"User Login Successfully...",Toast.LENGTH_SHORT).show();
                        editor.putString("Login",u);
                        editor.commit();
                        Intent i = new Intent(MainActivity.this,MainActivityHome.class);
                        i.putExtra("uname",u);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Username and Password Invalid...",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean checkUserLogin(String u, String p) {
                if (TextUtils.isEmpty(u)){
                    uname.setError("Please Enter Username");
                    uname.requestFocus();
                    return false;
                }else{
                    uname.setError(null);
                }

                if (TextUtils.isEmpty(p)){
                    pass.setError("Please Enter Password");
                    pass.requestFocus();
                    return false;
                }else{
                    pass.setError(null);
                }
                return true;
            }


        });
    }

    public void moveregis(View view) {
        Intent i = new Intent(MainActivity.this,MainActivityReg.class);
        startActivity(i);
        finish();
    }
}