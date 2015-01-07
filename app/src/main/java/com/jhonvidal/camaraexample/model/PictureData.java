package com.jhonvidal.camaraexample.model;

import android.net.Uri;
import android.widget.ImageView;

import java.net.URI;

/**
 * Created by jhonvidal on 29-12-14.
 */
public class PictureData {
    public ImageView imageView;
    public Boolean requerido;
    public Uri uri;

    public PictureData(ImageView imageView, Boolean requerido, Uri uri) {
        this.imageView = imageView;
        this.requerido = requerido;
        this.uri = uri;
    }
}
