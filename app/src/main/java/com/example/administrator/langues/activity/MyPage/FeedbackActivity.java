package com.example.administrator.langues.activity.MyPage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.langues.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;

public class FeedbackActivity extends AppCompatActivity {
    static final int PICTURE_DIALOG_ID = 2;
    ImageView feedback_pho;
    public String picpath;
    byte[] photobytes;

    ImageButton feedback_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback_pho= findViewById(R.id.feedback_pho);
        feedback_pho.setOnClickListener(v -> onCreateDialog(PICTURE_DIALOG_ID));
        feedback_return= findViewById(R.id.feedback_return);
        feedback_return.setOnClickListener(v -> finish());
    }
    // 对话框创建
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PICTURE_DIALOG_ID:
                new AlertDialog.Builder(this)
                        .setTitle("请选择图片来源")
                        .setNegativeButton("相册",
                                (dialog, which) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(
                                            Intent.ACTION_PICK, null);
                                    intent.setDataAndType(
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            "image/*");
                                    startActivityForResult(intent, 1);
                                })
                        .setPositiveButton("拍照",
                                (dialog, whichButton) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(
                                            MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(
                                                    Environment
                                                            .getExternalStorageDirectory(),
                                                    "temp.jpg")));
                                    startActivityForResult(intent, 2);
                                }).show();
        }
        return null;
    }
    //picture
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (data == null) { return; }
                startPhotoZoom(data.getData());
                break;
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case 3:
                if (data != null) { setPicToView(data); }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        FileOutputStream b;
        String name = new DateFormat().format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";
        File temp = new File(Environment.getExternalStorageDirectory() + "/"
                + name);
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            try {
                b = new FileOutputStream(temp);
                photo.compress(Bitmap.CompressFormat.JPEG, 75, b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            feedback_pho.setImageBitmap(photo);
            picpath = temp.getAbsolutePath();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            photobytes = stream.toByteArray();
        }
    }
}