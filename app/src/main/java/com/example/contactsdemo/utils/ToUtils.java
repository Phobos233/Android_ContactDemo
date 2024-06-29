package com.example.contactsdemo.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.contactsdemo.models.ContactClass;
import com.example.contactsdemo.models.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phobos
 */
public class ToUtils {
    DbUtils dbUtils;
    SQLiteDatabase db;




    public void initDatabase(Context mContext){

        dbUtils=new DbUtils(mContext);
        db=dbUtils.getWritableDatabase();

    }

    public void add(ContactClass contacts){
        db.execSQL("INSERT INTO contacts(name,tel) values(?,?)"
                ,new String[]{contacts.getName(),contacts.getTel()});
    }

    public void delete(int id){
        db.execSQL("DELETE FROM contacts WHERE contactid=?",new Object[]{id});
    }

    public void updates(@NonNull ContactClass contactClass){

        db.execSQL("UPDATE contacts SET name=?,tel=? WHERE contactid=?"
                ,new Object[]{contactClass.getName()
                        ,contactClass.getTel()
                        ,contactClass.getId()});

    }
    public Users login(@NonNull Users user){
        Cursor cursor=db.rawQuery("SELECT * FROM users WHERE username=? AND password = ?"
                ,new String[]{user.getUsername(), user.getPassword()});
        if(cursor.moveToFirst()){
            int userid=cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                            "userid"));
            String username=cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            "username"));
            String password=cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            "password"));
            return new Users(userid,username,password);
        }
        cursor.close();
        return null;
    }
    public void register(@NonNull Users user){
        db.execSQL("INSERT INTO users (username,password) values(?,?)"
                ,new String[]{user.getUsername(), user.getPassword()});

    }
    public ArrayList<ContactClass> search(){
        Cursor cursor=db.rawQuery("SELECT * FROM contacts",null);
        if(cursor.moveToFirst()){
            ArrayList<ContactClass> contactsData =new ArrayList<>();
            do {
                ContactClass contact=new ContactClass();
                contact.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                contact.setTel(cursor.getString(cursor.getColumnIndexOrThrow("tel")));
                contact.setId(cursor.getInt(cursor.getColumnIndexOrThrow("contactid")));
                contactsData.add(contact);
            }
            while(cursor.moveToNext());
            return contactsData;
        }
        cursor.close();
        return null;
    }
    public List<ContactClass> search(String str){

        String sql="SELECT * FROM contacts WHERE name LIKE '%"+str+"%' OR tel LIKE '%"+str+"%'";
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            List<ContactClass> contactsData =new ArrayList<>();
            do {
                ContactClass contact=new ContactClass();
                contact.setName(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "name")));
                contact.setTel(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "tel")));
                contact.setId(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        "contactid")));
                contactsData.add(contact);
            }
            while(cursor.moveToNext());
            return contactsData;
        }
        cursor.close();
        return null;
    }

}
