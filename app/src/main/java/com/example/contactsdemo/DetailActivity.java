package com.example.contactsdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.contactsdemo.models.ContactClass;
import com.example.contactsdemo.utils.ToUtils;

/**
 * @author Phobos
 */
public class DetailActivity extends AppCompatActivity {

    private ImageView imgIcon;
    private TextView tvContactName;
    private TextView tvContactTel;
    private Toolbar toolbar;
    private ToUtils toUtils=new ToUtils();
    private Button btnCall;
    private Button btnSendMsg;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Intent itRec=getIntent();
        ContactClass contactRec=(ContactClass) itRec.getSerializableExtra("contact");
        bindViews();

        if (contactRec != null) {
            tvContactName.setText(contactRec.getName());
            tvContactTel.setText(contactRec.getTel());
        }
        toolbar.setOnMenuItemClickListener(item -> {
            int id= item.getItemId();
            if (id==R.id.edit){
                Intent editIt=new Intent(DetailActivity.this
                        ,EditActivity.class);
                editIt.putExtra("contact",contactRec);
                startActivity(editIt);
                finish();
            }else if (id==R.id.delete){
                toUtils.initDatabase(DetailActivity.this);
                MainActivity.mAct.finish();
                toUtils.delete(contactRec.getId());
                Intent reloadIt=new Intent(DetailActivity.this,MainActivity.class);
                startActivity(reloadIt);
                finish();
            }
            return false;
        });

    }
    public void bindViews(){
        imgIcon=findViewById(R.id.img_icon_details);
        tvContactName=findViewById(R.id.contactName_Details);
        tvContactTel=findViewById(R.id.contactTel);
        btnCall=findViewById(R.id.btnCall);
        btnSendMsg=findViewById(R.id.btnSendMsg);
        toolbar=findViewById(R.id.toolbar_details);
        toolbar.inflateMenu(R.menu.details_menu);


        btnCall.setOnClickListener(v -> {
            Uri uri=Uri.parse("tel:"+tvContactTel.getText().toString());
            Intent callIt=new Intent(Intent.ACTION_DIAL,uri);
            startActivity(callIt);
        });
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("smsto:"+tvContactTel.getText().toString());
                Intent msgIt=new Intent(Intent.ACTION_SENDTO,uri);
                startActivity(msgIt);
            }
        });

    }
}
