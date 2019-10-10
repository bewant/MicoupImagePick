package com.missycoupons.fishbun.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.missycoupons.fishbun.define.Define;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by reactlibrary on 16/01/2017.
 */

public class CameraUtil {

    private String savePath;
    private static CameraUtil cameraUtil;

    private CameraUtil() {
        super();
    }

    public static CameraUtil getInstance() {
        if (cameraUtil == null) {
            cameraUtil = new CameraUtil();
        }
        return cameraUtil;
    }

    public void takePicture(Activity activity, String saveDir) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(saveDir); //make a file
                setSavePath(photoFile.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(activity,
                            activity.getApplicationContext().getPackageName() + ".provider", photoFile);
                } else {
                    uri = Uri.fromFile(photoFile);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                activity.startActivityForResult(takePictureIntent, Define.TAKE_A_PICK_REQUEST_CODE);
            }
        }
    }

    private File createImageFile(String saveDir) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "/JPEG_" + timeStamp + "_";
        File storageDir = new File(saveDir + imageFileName + ".jpg");
        storageDir.createNewFile();
        /*
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        */
        return storageDir;
    }


    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
