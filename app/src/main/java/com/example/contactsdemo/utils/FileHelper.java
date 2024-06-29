package com.example.contactsdemo.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Phobos
 */
public class FileHelper {
    private Context mContext;

    public FileHelper() {
    }


    public FileHelper(Context mContext) {
        super();
        this.mContext = mContext;
    }

    File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    public void save(String filename, String fileContent) throws Exception {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filename =Environment.getExternalStorageDirectory().getCanonicalPath()  + "/" + filename;
            FileOutputStream output = new FileOutputStream(filename);
            output.write(fileContent.getBytes());
            output.close();
        } else {
            Toast.makeText(mContext, "SD卡不存在或者不可读写", Toast.LENGTH_LONG).show();
        }
    }
    
}
