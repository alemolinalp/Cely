package com.example.alexandramolina.cely;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PrincipalActivity  extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_principal,container,false);



        ImageView iv= view.findViewById(R.id.imageView2);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserBBC();
            }
        });
        iv= view.findViewById(R.id.imageView3);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserBusinessInsider();
            }
        });
        iv= view.findViewById(R.id.imageView4);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserCNN();
            }
        });
        iv= view.findViewById(R.id.imageView5);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserElMundo();
            }
        });
        iv= view.findViewById(R.id.imageView6);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserLaNacion();
            }
        });
        iv= view.findViewById(R.id.imageView7);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browserEconomist();
            }
        });


        return view;
    }



    public void browserBBC(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bbc.com/news"));
        startActivity(browserIntent);
    }
    public void browserCNN(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://edition.cnn.com"));
        startActivity(browserIntent);
    }
    public void browserLaNacion(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nacion.com"));
        startActivity(browserIntent);
    }
    public void browserBusinessInsider(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.businessinsider.com"));
        startActivity(browserIntent);
    }
    public void browserElMundo(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.elmundo.es"));
        startActivity(browserIntent);
    }
    public void browserEconomist(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.economist.com"));
        startActivity(browserIntent);
    }



}
