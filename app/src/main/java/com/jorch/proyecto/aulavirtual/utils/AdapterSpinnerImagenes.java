package com.jorch.proyecto.aulavirtual.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jorch.proyecto.aulavirtual.R;

import java.util.List;

/**
 * Created by JORCH on 19/01/2017.
 */

public class AdapterSpinnerImagenes extends ArrayAdapter {
    private List<Integer> list;
    private ImageView imageView;
    public AdapterSpinnerImagenes(Context context, List<Integer> objects) {
        super(context, R.layout.spinner_imagen_cursos, objects);
        list = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_imagen_cursos,parent,false);
        }
        imageView = (ImageView) view.findViewById(R.id.img_spinner_image_curso);
        Glide.with(imageView.getContext())
                .load(list.get(position))
                .into(imageView);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }

}
