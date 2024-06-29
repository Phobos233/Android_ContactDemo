package com.example.contactsdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsdemo.models.Users;
import com.example.contactsdemo.utils.DbUtils;
import com.example.contactsdemo.utils.SharedHelper;
import com.example.contactsdemo.utils.ToUtils;

/**
 * @author Phobos
 */
public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;
    private EditText etUsername;
    private EditText etPassword;


    private SQLiteDatabase contactsData;
    private ToUtils toUtils=new ToUtils();

    public  Context mContext;
    private SharedHelper sh;

    public void bindViews(){
        btnLogin=findViewById(R.id.login_button_login);
        btnRegister=findViewById(R.id.Reg_button_login);
        etUsername=findViewById(R.id.username_login);
        etPassword=findViewById(R.id.password_login);
        mContext=getApplicationContext();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        toUtils.initDatabase(LoginActivity.this);
        sh=new SharedHelper(mContext);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Users user=new Users();
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                Users currentUser=toUtils.login(user);
                if (currentUser!=null){
                    MainActivity.mAct.finish();
                    Toast.makeText(LoginActivity.this,
                            "登录成功",
                            Toast.LENGTH_SHORT).show();
                    sh.save(currentUser.getUsername()
                            ,currentUser.getPassword());
                    Intent backIt=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(backIt);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"用户不存在或密码错误",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIt=new Intent(LoginActivity.this
                        , RegActivity.class);
                startActivity(regIt);
                finish();

            }
        });

    }

}
