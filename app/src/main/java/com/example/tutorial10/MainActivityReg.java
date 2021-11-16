package com.example.tutorial10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityReg extends AppCompatActivity {

    EditText name,uname,phone,pass,cpass;
    Button regis;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reg);

        name = findViewById(R.id.txtrname);
        uname = findViewById(R.id.txtruname);
        phone = findViewById(R.id.txtrphone);
        pass = findViewById(R.id.txtrpass);
        cpass = findViewById(R.id.txtrcpass);
        regis = findViewById(R.id.btnreg);
        DB = new DBHelper(this);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = name.getText().toString().trim();
                String u = uname.getText().toString().trim();
                String ph = phone.getText().toString().trim();
                String p = pass.getText().toString().trim();
                String c = cpass.getText().toString().trim();

                if(createNewAcount(s,u,ph,p,c)) {
                    boolean insertdata = DB.insert(s,u,ph,p);
                    if(insertdata == true){
                        Toast.makeText(getApplicationContext(),"User Registered Successfully...",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivityReg.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"User Registration Failed...",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean createNewAcount(String s, String u, String ph, String p, String c) {
                if (TextUtils.isEmpty(s)){
                    name.setError("Please Enter Name");
                    name.requestFocus();
                    return false;
                }else{
                    name.setError(null);
                }

                if (TextUtils.isEmpty(ph)){
                    phone.setError("Please Enter Phone Number");
                    phone.requestFocus();
                    return false;
                }else{
                    phone.setError(null);
                }

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

                if (TextUtils.isEmpty(c)){
                    cpass.setError("Please Enter CPassword");
                    cpass.requestFocus();
                    return false;
                }else{
                    cpass.setError(null);
                }

                if(!c.equals(p)){
                    cpass.setError("Password not Match");
                    cpass.requestFocus();
                    return false;
                }else{
                    cpass.setError(null);
                }

                return true;
            }

        });
    }
}