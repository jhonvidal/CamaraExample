package com.jhonvidal.camaraexample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jhonvidal.camaraexample.model.PictureData;
import com.jhonvidal.camaraexample.util.PhotoUtils;
import com.jhonvidal.camaraexample.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ExampleCameraStaticActivity extends ActionBarActivity {

    private Button button;
    private LinearLayout layout;
    private PhotoUtils photoUtils;
    private List<PictureData> pictureDatas = new ArrayList<PictureData>();
    final int CAPTURE_IMAGE1 = 2;
    final int CAPTURE_IMAGE2 = 3;
    final int CAPTURE_IMAGE3 = 4;
    private String selectedImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPictureData();
        button = new Button(this);
        button.setText("Register");
        LinearLayout linearLayout = new LinearLayout(this);
        layout = (LinearLayout) View.inflate(this, R.layout.activity_example_camera_static, null);
        layout.addView(pictureDatas.get(0).imageView);
        layout.addView(pictureDatas.get(1).imageView);
        layout.addView(pictureDatas.get(2).imageView);

        linearLayout.addView(button);
        layout.addView(linearLayout);
        photoUtils = new PhotoUtils(this);
        setClickItemImage();
        setContentView(layout);
    }

    private void loadPictureData() {

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) new LinearLayout.LayoutParams(Util.getDpTpPx(this, 90), Util.getDpTpPx(this, 90));
        params.setMargins(Util.getDpTpPx(this, 2), Util.getDpTpPx(this, 0), Util.getDpTpPx(this, 2), Util.getDpTpPx(this, 0));

        ImageView imageView1 = new ImageView(this);
        imageView1.setLayoutParams(params);
        imageView1.setId(Util.generateViewId());
        imageView1.setImageResource(R.drawable.ic_launcher);
        ImageView imageView2 = new ImageView(this);
        imageView2.setLayoutParams(params);
        imageView2.setId(Util.generateViewId());
        imageView2.setImageResource(R.drawable.ic_launcher);
        ImageView imageView3 = new ImageView(this);
        imageView3.setLayoutParams(params);
        imageView3.setId(Util.generateViewId());
        imageView3.setImageResource(R.drawable.ic_launcher);

        Log.e("ID1", String.valueOf(imageView1.getId()));
        Log.e("ID2", String.valueOf(imageView2.getId()));
        Log.e("ID3", String.valueOf(imageView3.getId()));

        PictureData pictureData1 = new PictureData(imageView1, true, null);
        PictureData pictureData2 = new PictureData(imageView2, false, null);
        PictureData pictureData3 = new PictureData(imageView3, false, null);

        pictureDatas.add(pictureData1);
        pictureDatas.add(pictureData2);
        pictureDatas.add(pictureData3);
    }

    private void setClickItemImage() {

        pictureDatas.get(0).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        "android.media.action.IMAGE_CAPTURE");
                File photo = null;

                try {
                    // place where to store camera taken picture
                    photo = PhotoUtils.createTemporaryFile("picture", ".jpg", ExampleCameraStaticActivity.this);
                    photo.delete();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(),
                            "Can't create file to take picture!");
                }

                pictureDatas.get(0).uri = Uri.fromFile(photo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureDatas.get(0).uri);
                startActivityForResult(intent, pictureDatas.get(0).imageView.getId());
            }
        });

        pictureDatas.get(1).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        "android.media.action.IMAGE_CAPTURE");
                File photo = null;

                try {
                    // place where to store camera taken picture
                    photo = PhotoUtils.createTemporaryFile("picture", ".jpg", ExampleCameraStaticActivity.this);
                    photo.delete();
                } catch (Exception e) {
                    Log.v(getClass().getSimpleName(),
                            "Can't create file to take picture!");
                }

                pictureDatas.get(1).uri = Uri.fromFile(photo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureDatas.get(1).uri);
                startActivityForResult(intent, pictureDatas.get(1).imageView.getId());
            }
        });

        pictureDatas.get(2).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        "android.media.action.IMAGE_CAPTURE");
                File photo = null;

                try {
                    // place where to store camera taken picture
                    photo = PhotoUtils.createTemporaryFile("picture", ".jpg", ExampleCameraStaticActivity.this);
                    photo.delete();
                } catch (Exception e) {
                    Log.v(getClass().getSimpleName(),
                            "Can't create file to take picture!");
                }

                pictureDatas.get(2).uri = Uri.fromFile(photo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureDatas.get(2).uri);
                startActivityForResult(intent, pictureDatas.get(2).imageView.getId());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImages();
            }
        });

    }

    private void saveImages() {

        String pathDir = getExternalCacheDir().getAbsolutePath() + "/temp/";
        String nameFile1 = pictureDatas.get(0).uri.getLastPathSegment();
        String nameFile2 = pictureDatas.get(1).uri.getLastPathSegment();
        String nameFile3 = pictureDatas.get(2).uri.getLastPathSegment();
        Util.moveFile(pathDir, nameFile1, Environment.getExternalStorageDirectory().getPath() + "/JhonKimberly/");
        Util.moveFile(pathDir, nameFile2, Environment.getExternalStorageDirectory().getPath() + "/JhonKimberly/");
        Util.moveFile(pathDir, nameFile3, Environment.getExternalStorageDirectory().getPath() + "/JhonKimberly/");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (PictureData pictureData : pictureDatas) {

            if (pictureData.imageView != null && requestCode == pictureData.imageView.getId()
                    && resultCode == RESULT_OK) {
                getImage(pictureData.uri, pictureData);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_example_camara_static, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getImage(Uri uri, PictureData pictureData) {
        Bitmap bounds = photoUtils.getImage(uri);
        if (bounds != null) {
            setImageBitmap(bounds, pictureData);
        } else {
            showErrorToast();
        }
    }

    private void showErrorToast() {
        Log.e("ERROR", "ERROR AL CARGAR");
    }

    private void setImageBitmap(Bitmap bitmap, PictureData pictureData) {
        pictureData.imageView.setImageBitmap(bitmap);
    }
}
