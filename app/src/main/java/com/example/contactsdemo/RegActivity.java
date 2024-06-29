package com.example.contactsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsdemo.models.Users;
import com.example.contactsdemo.utils.ToUtils;

/**
 * @author Phobos
 */
public class RegActivity extends AppCompatActivity {

    private EditText etUsernameReg;
    private EditText etPasswordReg;
    private Button btnRegisterReg;

    private ToUtils toUtils=new ToUtils();

    public void bindViews(){
        etPasswordReg=findViewById(R.id.password_reg);
        etUsernameReg=findViewById(R.id.username_reg);
        btnRegisterReg=findViewById(R.id.reg_button_reg);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        bindViews();
        btnRegisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUtils.initDatabase(RegActivity.this);
                Users newUser=new Users();
                newUser.setUsername(etUsernameReg.getText().toString());
                newUser.setPassword(etPasswordReg.getText().toString());
                toUtils.register(newUser);
                Intent logIt=new Intent(RegActivity.this, LoginActivity.class);
                startActivity(logIt);
                finish();
            }
        });
    }
}
