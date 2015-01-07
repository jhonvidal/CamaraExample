package com.jhonvidal.camaraexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jhonvidal.camaraexample.model.PictureData;
import com.jhonvidal.camaraexample.util.PhotoUtils;
import com.jhonvidal.camaraexample.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private LinearLayout layout;

    private AlertDialog _photoDialog;
    private static final int ACTIVITY_SELECT_IMAGE = 1020;
    private static final int ACTIVITY_SELECT_FROM_CAMERA = 1040;
    private static final int ACTIVITY_SHARE = 1030;
    private static int ACTIVITY_SELECT = 0;

    private PhotoUtils photoUtils;
    private List<PictureData> pictureDatas = new ArrayList<PictureData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPictureData();
        layout = (LinearLayout) View.inflate(this, R.layout.activity_main, null);
        layout.addView(pictureDatas.get(0).imageView);
        layout.addView(pictureDatas.get(1).imageView);
        layout.addView(pictureDatas.get(2).imageView);
        photoUtils = new PhotoUtils(this);
        setClickItemImage();
        setContentView(layout);
    }

    private void setClickItemImage() {

        pictureDatas.get(0).imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!getPhotoDialog(pictureDatas.get(0)).isShowing() && !isFinishing())
                    getPhotoDialog(pictureDatas.get(0)).show();
            }
        });

        pictureDatas.get(1).imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!getPhotoDialog(pictureDatas.get(1)).isShowing() && !isFinishing())
                    getPhotoDialog(pictureDatas.get(1)).show();
            }
        });

        pictureDatas.get(2).imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!getPhotoDialog(pictureDatas.get(2)).isShowing() && !isFinishing())
                    getPhotoDialog(pictureDatas.get(2)).show();
            }
        });

    }

    private void loadPictureData() {

        ImageView imageView1 = new ImageView(this);
        imageView1.setId(Util.generateViewId());
        imageView1.setImageResource(R.drawable.ic_launcher);
        ImageView imageView2 = new ImageView(this);
        imageView2.setId(Util.generateViewId());
        imageView2.setImageResource(R.drawable.ic_launcher);
        ImageView imageView3 = new ImageView(this);
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

    private AlertDialog getPhotoDialog(final PictureData pictureData) {
        if (_photoDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this);
            builder.setTitle(R.string.photo_source);
            builder.setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    File photo = null;
                    try {
                        // place where to store camera taken picture
                        photo = PhotoUtils.createTemporaryFile("picture", ".jpg", MainActivity.this);
                        photo.delete();
                    } catch (Exception e) {
                        Log.v(getClass().getSimpleName(),
                                "Can't create file to take picture!");
                    }
                    ACTIVITY_SELECT = ACTIVITY_SELECT_FROM_CAMERA;
                    pictureData.uri = Uri.fromFile(photo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureData.uri);
                    startActivityForResult(intent, pictureData.imageView.getId());

                }

            });
            builder.setNegativeButton(R.string.gallery, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ACTIVITY_SELECT = ACTIVITY_SELECT_IMAGE;
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, pictureData.imageView.getId());
                }

            });
            _photoDialog = builder.create();

        }
        return _photoDialog;

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        int i = 1;
//        for (PictureData pictureData : pictureDatas) {
//            if (pictureData.uri != null)
//                outState.putString("Uri" + String.valueOf(i), pictureData.uri.toString());
//            i = i + 1;
//        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        int i = 1;
//        for (PictureData pictureData : pictureDatas) {
//            if (savedInstanceState.containsKey("Uri" + String.valueOf(i))) {
//                pictureData.uri = Uri.parse(savedInstanceState.getString("Uri") + String.valueOf(i));
//            }
//            i = i + 1;
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (PictureData pictureData : pictureDatas) {

            if (requestCode == pictureData.imageView.getId() && resultCode == RESULT_OK && ACTIVITY_SELECT == ACTIVITY_SELECT_IMAGE) {
                pictureData.uri = data.getData();
                getImage(pictureData.uri, pictureData);
            } else {
                if (pictureData.imageView != null && requestCode == pictureData.imageView.getId()
                        && resultCode == RESULT_OK) {
                    getImage(pictureData.uri, pictureData);
                }
            }
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


}
