package com.example.mymessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentCallbacks;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
MaterialEditText username,password,email;
Button btn_register;
FirebaseAuth auth;
DatabaseReference reference;
CheckBox showpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showpassword=findViewById(R.id.checkBox);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        btn_register=findViewById(R.id.btn_register);

        auth=FirebaseAuth.getInstance();


        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                                                if (b) {
                                                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                                } else {

                                                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                }
                                            }
                                        }

        );




        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username= username.getText().toString().trim();
                String txt_password= password.getText().toString().trim();
                String txt_email= email.getText().toString().trim();

                if(TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_password)||TextUtils.isEmpty(txt_email)){
                    Toast.makeText(RegisterActivity.this,"All fileds are required",Toast.LENGTH_SHORT).show();
                }else if(txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this,"password must be at least 6 characters",Toast.LENGTH_SHORT).show();

                }else{
                    register(txt_username,txt_email,txt_password);
                }
            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    private void register(String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "You cant't register with this email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
/*public boolean v(String txt_password)
{
    Pattern lowercase=Pattern.compile("a-z");
    Pattern digitcase=Pattern.compile("0-9");
 boolean r=true;

    if(!lowercase.matcher((CharSequence) password).find()) {
        tv_1.setTextColor(Color.RED);
        r=false;
    }
    else{
        tv_1.setTextColor(Color.GREEN);
    }
    if(!digitcase.matcher((CharSequence) password).find())
    {
        tv_2.setTextColor(Color.RED);
        r=false;
    }
    else
    {
        tv_2.setTextColor(Color.GREEN);
    }
    if(txt_password.length()<6)
    {
        tv_3.setTextColor(Color.RED);
         r=false;
    }

    else{
        tv_3.setTextColor(Color.GREEN);
    }
    return r;
}*/




}

