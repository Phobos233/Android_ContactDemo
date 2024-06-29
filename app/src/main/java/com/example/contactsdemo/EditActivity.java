package com.example.contactsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
public class EditActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etTel;
    private Button btnSave;
    private Toolbar toolbar;
    public ToUtils toUtils=new ToUtils();
    public ContactClass contactRec;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        bindViews();
        Intent itRec=getIntent();

        contactRec=(ContactClass) itRec.getSerializableExtra("contact");
        if (contactRec != null) {
            etName.setText(contactRec.getName());
            etTel.setText(contactRec.getTel());
        }
        toUtils.initDatabase(EditActivity.this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactClass contact=new ContactClass();
                contact.setName(etName.getText().toString());
                contact.setTel(etTel.getText().toString());
                contact.setId(contactRec.getId());
                toUtils.updates(contact);
                MainActivity.mAct.finish();
                Intent mIt=new Intent(EditActivity.this,MainActivity.class);
                startActivity(mIt);
                finish();
            }
        });
    }
    public void bindViews(){
        etName=findViewById(R.id.name_edit);
        etTel=findViewById(R.id.tel_edit);
        btnSave=findViewById(R.id.btnSave_edit);
        toolbar=findViewById(R.id.toolbar_Edit);
        toolbar.inflateMenu(R.menu.edit_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id= item.getItemId();
                if (id==R.id.save_edit){
                    ContactClass contact=new ContactClass();
                    contact.setName(etName.getText().toString());
                    contact.setTel(etTel.getText().toString());
                    contact.setId(contactRec.getId());
                    toUtils.updates(contact);
                    MainActivity.mAct.finish();
                    Intent mIt=new Intent(EditActivity.this,MainActivity.class);
                    startActivity(mIt);
                    finish();
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
