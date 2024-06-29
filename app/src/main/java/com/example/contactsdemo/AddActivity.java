package com.example.contactsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.contactsdemo.models.ContactClass;
import com.example.contactsdemo.utils.ToUtils;

/**
 * @author Phobos
 */
public class AddActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etTel;
    private Button btnSave;
    private Toolbar toolbar;
    public ToUtils toUtils=new ToUtils();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        bindViews();
        toUtils.initDatabase(AddActivity.this);
        btnSave.setOnClickListener(v -> {
            ContactClass contact=new ContactClass();
            contact.setName(etName.getText().toString());
            contact.setTel(etTel.getText().toString());
            toUtils.add(contact);
            MainActivity.mAct.finish();
            Intent mIt=new Intent(AddActivity.this,MainActivity.class);
            startActivity(mIt);
            finish();
        });
    }
    public void bindViews(){
        etName=findViewById(R.id.name_add);
        etTel=findViewById(R.id.tel_add);
        btnSave=findViewById(R.id.btnSave_add);
        toolbar=findViewById(R.id.toolbar_Add);
        toolbar.inflateMenu(R.menu.edit_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            int id= item.getItemId();
            if (id==R.id.save_edit){
                ContactClass contact=new ContactClass();
                contact.setName(etName.getText().toString());
                contact.setTel(etTel.getText().toString());
                toUtils.add(contact);
                MainActivity.mAct.finish();
                Intent mIt=new Intent(AddActivity.this,MainActivity.class);
                startActivity(mIt);
                finish();
            }
            return false;
        });
        toolbar.setNavigationOnClickListener(v -> finish());
    }

}


