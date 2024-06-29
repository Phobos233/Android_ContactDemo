package com.example.contactsdemo;

import static android.provider.ContactsContract.CommonDataKinds;
import static android.provider.ContactsContract.Data;
import static android.provider.ContactsContract.RawContacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.contactsdemo.adapter.ContactsAdapter;
import com.example.contactsdemo.models.ContactClass;
import com.example.contactsdemo.utils.FileHelper;
import com.example.contactsdemo.utils.SharedHelper;
import com.example.contactsdemo.utils.ToUtils;
import com.example.contactsdemo.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.TelephoneType;

/**
 * @author Phobos
 */
public class MainActivity extends AppCompatActivity {
    int flag=0;
    private Toolbar toolbar;
    private EditText editTextSearch;
    public Context mContext;
    private ListView listView;
    private ToUtils toUtils=new ToUtils();
    static MainActivity mAct=null;
    private SharedHelper sh;
    private ToolUtils toolUtils=new ToolUtils();
    ContactsAdapter contactsAdapter;
    List<ContactClass> arrayList;
    LayoutInflater inflater;
    String title_login="登录";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        toUtils.initDatabase(mContext);
        requestPermission();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestPermissionAll();
        }
        if (toUtils.search()!=null){
        arrayList=toUtils.search();
        arrayList.sort((o1, o2) -> toolUtils.getCap(o1.getName()).compareTo(toolUtils.getCap(o2.getName())));
        contactsAdapter=new ContactsAdapter(arrayList,inflater);

        listView.setAdapter(contactsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactClass contactClassBundle=new ContactClass();
                contactClassBundle.setId(arrayList.get(position).getId());
                contactClassBundle.setName( arrayList.get(position).getName());
                contactClassBundle.setTel(arrayList.get(position).getTel());
                Intent detailIt=new Intent(MainActivity.this
                                ,DetailActivity.class);
                detailIt.putExtra("contact",contactClassBundle);
                startActivity(detailIt);
            }
        });
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflaterMain=new MenuInflater(this);
        inflaterMain.inflate(R.menu.contacts_context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position= 0;
        int id=item.getItemId();
        if (info != null) {
            position = info.position;
        }

        if (id==R.id.detail_context){
            ContactClass contactClassBundle=new ContactClass();
            contactClassBundle.setId(arrayList.get(position).getId());
            contactClassBundle.setName( arrayList.get(position).getName());
            contactClassBundle.setTel(arrayList.get(position).getTel());
            Intent detailIt=new Intent(MainActivity.this
                    ,DetailActivity.class);
            detailIt.putExtra("contact",contactClassBundle);
            startActivity(detailIt);
        } else if (id==R.id.delete_context){
            toUtils.initDatabase(MainActivity.this);
            toUtils.delete(arrayList.get(position).getId());
            if (arrayList!=null){
                arrayList.clear();
                arrayList=new ArrayList<>();
            }
            arrayList=toUtils.search();
            arrayList.sort((o1, o2) -> toolUtils.getCap(o1.getName()).compareTo(toolUtils.getCap(o2.getName())));
            contactsAdapter=new ContactsAdapter(arrayList,inflater);
            listView.setAdapter(contactsAdapter);
            Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();


        } else if (id==R.id.edit_context) {
            ContactClass contactClassBundle=new ContactClass();
            contactClassBundle.setId(arrayList.get(position).getId());
            contactClassBundle.setName( arrayList.get(position).getName());
            contactClassBundle.setTel(arrayList.get(position).getTel());
            Intent editIt=new Intent(MainActivity.this
                    ,EditActivity.class);
            editIt.putExtra("contact",contactClassBundle);
            startActivity(editIt);
        }

        return super.onContextItemSelected(item);
    }


    public void bindViews(){
        toolbar =findViewById(R.id.mainToolbar);
        editTextSearch=findViewById(R.id.search_string);
        listView=findViewById(R.id.ContactsData);
        toolbar.inflateMenu(R.menu.main_menu);
        invalidateOptionsMenu();
        mContext=MainActivity.this;
        sh=new SharedHelper(mContext);
        inflater = LayoutInflater.from(this);
        mAct=MainActivity.this;
        registerForContextMenu(listView);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (arrayList!=null){
                    arrayList.clear();
                    arrayList=new ArrayList<>();
                }
                String key= editTextSearch.getText().toString();
                if (!key.isEmpty()){
                    arrayList=toUtils.search(key);
                }else {
                    arrayList=toUtils.search();
                }

                System.out.println(key);

                if (arrayList!=null){
                    arrayList.sort((o1, o2) -> toolUtils.getCap(o1.getName()).compareTo(toolUtils.getCap(o2.getName())));
                    System.out.println(key);
                    contactsAdapter=new ContactsAdapter(arrayList,inflater);
                    listView.setAdapter(contactsAdapter);
                }else {
                    listView.setAdapter(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        toolbar.setOnMenuItemClickListener(item -> {
            int id= item.getItemId();
            if (id==R.id.search_button){
                if (flag==0){
                    editTextSearch.setVisibility(View.VISIBLE);
                    flag=1;
                }else {
                    editTextSearch.setVisibility(View.INVISIBLE);
                    flag=0;
                }
            }else if(id==R.id.login_button){
                String title= String.valueOf(item.getTitle());
                System.out.println(title);
                if (title_login.equals(title)){
                    if (sh.read().get("username")==""){
                    Intent loginIt=new Intent(MainActivity.this
                            ,LoginActivity.class);
                    startActivity(loginIt);
                }else{
                        item.setTitle("退出登录");
                        Toast.makeText(mContext, "您已登录,用户："+sh.read().get("username"), Toast.LENGTH_LONG).show();
                }
                } else if ("退出登录".equals(title)) {
                    sh.delete();
                    Toast.makeText(mContext, "您已退出登录", Toast.LENGTH_LONG).show();
                    recreate();
                }

            }else if(id==R.id.add_button){
                Intent addIt=new Intent(MainActivity.this
                        ,AddActivity.class);
                startActivity(addIt);
            }else if (id==R.id.load_button){
                ArrayList<ContactClass> arrayList=toUtils.search();
                for (int i=0;i<arrayList.size();i++){
                    loadContacts(arrayList.get(i));
                }
                Intent reloadIt=new Intent(MainActivity.this,MainActivity.class);
                startActivity(reloadIt);
                Toast.makeText(mContext, "加载至通讯录成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if (id==R.id.backup_button) {
                backupContacts();
                Intent reloadIt=new Intent(MainActivity.this,MainActivity.class);
                startActivity(reloadIt);
                Toast.makeText(mContext, "备份至通讯录成功", Toast.LENGTH_SHORT).show();
                finish();
            }else if (id==R.id.output_button) {
                FileHelper fileHelper=new FileHelper(mContext);
                for (int i=0;i<arrayList.size();i++){
                    VCard vCard=new VCard();
                    vCard.setFormattedName(arrayList.get(i).getName());
                    vCard.addTelephoneNumber(arrayList.get(i).getTel(), TelephoneType.CELL);
                    vCard.setVersion(VCardVersion.V2_1);
                    String vCardStr= Ezvcard.write(vCard).go();
                    System.out.println(vCardStr);
                    try {
                        fileHelper.save("myContacts.vcf",vCardStr);
                        Toast.makeText(getApplicationContext(), "数据写入成功", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "数据写入失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            return false;
        });

    }
    public void backupContacts(){

        ContentResolver resolver=getContentResolver();
        Uri uri= CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor=resolver.query(uri,null,null,null,null);
        if (cursor != null) {
            while (cursor.moveToNext()){
                ContactClass contactClass=new ContactClass();
                contactClass.setName(cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                CommonDataKinds.Phone.DISPLAY_NAME)));
                contactClass.setTel(cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                CommonDataKinds.Phone.NUMBER)));
                toUtils.add(contactClass);
            }
            cursor.close();
        }
    }
    public void loadContacts(@NonNull ContactClass contactClass){
        ContentResolver resolver=getContentResolver();
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();
        //
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE
                , CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.StructuredName.GIVEN_NAME
                , contactClass.getName());
        resolver.insert(Data.CONTENT_URI, values);
        values.clear();
        //
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE
                , CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, contactClass.getTel());
        values.put(CommonDataKinds.Phone.TYPE
                , CommonDataKinds.Phone.TYPE_MOBILE);
        resolver.insert(Data.CONTENT_URI, values);
        values.clear();
    }

    private void requestPermission(){
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CALL_PHONE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.SEND_SMS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            Toast.makeText(this, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void requestPermissionAll(){
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), 1002);
        } else {
            Toast.makeText(this, "多个权限你都有了，不用再次申请", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1002:
                // 1002请求码对应的是申请多个权限
                if (grantResults.length > 0) {
                    // 因为是多个权限，所以需要一个循环获取每个权限的获取情况
                    for (int i = 0; i < grantResults.length; i++) {
                        // PERMISSION_DENIED 这个值代表是没有授权，我们可以把被拒绝授权的权限显示出来
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                            Toast.makeText(MainActivity.this, permissions[i] + "权限被拒绝了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.login_button);
        if (sh.read()!=null){
            menuItem.setTitle("退出登录");
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
