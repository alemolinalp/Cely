package com.example.alexandramolina.cely;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoticiasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    String linkP1 = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=6ea91d289e6e4e53adb8eec7b039bc97";
    String linkP = "";
    String l = "";
    ArrayList<News> news = new ArrayList<>();
    NewsAdapter adapter;
    GridView gridView3;
    ActionBar actionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences sharedPreferences;
    NavigationView nv;
    String page = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        gridView3 = findViewById(R.id.gridView3);

        setNavigationViewListner();
        nv=findViewById(R.id.navigation_view);

        buscarNoticia();

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#233a62")));

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHeader();
    }

    private void setNavigationViewListner(){
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setHeader(){
        sharedPreferences = getSharedPreferences("com.example.alexandramolina.cely", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String name = sharedPreferences.getString("name", "");
        View header = nv.getHeaderView(0);
        TextView headerEmail =  header.findViewById(R.id.headerEmail);
        TextView headerName =  header.findViewById(R.id.headerName);
        headerEmail.setText(email);
        headerName.setText(name);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.convertidor:{
                abrirActivityConvertidor();
                Toast.makeText(this, "Convertidor", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.usuario:{
                abrirActivityUsuario();
                Toast.makeText(this, "Usuario", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.archivos:{
                abrirActivityArchivos();
                Toast.makeText(this, "Archivos", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.paginasSugeridas:{
                abrirActivityPrincipal();
                Toast.makeText(this, "Paginas sugeridas", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.noticias:{
                abrirActivityNoticias();
                Toast.makeText(this, "Noticias", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.traductor:{
                abrirActivityTraductor();
                Toast.makeText(this, "Traductor", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cerrarSesion:{
                abrirMainActivity();
                Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.noticiaBuscar:{
                abrirActivityBuscarNoticia();
                Toast.makeText(this,"Buscar Noticia", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.GPS:{
                abrirActivityGPS();
                Toast.makeText(this,"GPS", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
    public void abrirActivityConvertidor(){
        Intent intent = new Intent(this, Translation.class);
        startActivity(intent);
    }

    public void abrirActivityPrincipal(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void abrirActivityUsuario(){
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
    }
    public void abrirActivityArchivos(){
        Intent intent = new Intent(this, ArchivosActivity.class);
        startActivity(intent);
    }
    public void abrirActivityNoticias(){
        Intent intent = new Intent(this, NoticiasActivity.class);
        startActivity(intent);
    }
    public void abrirActivityTraductor(){
        Intent intent = new Intent(this, TraductorActivity.class);
        startActivity(intent);
    }
    public void abrirActivityBuscarNoticia(){
        Intent intent = new Intent(this, BuscarNoticiaActivity.class);
        startActivity(intent);
    }
    public void abrirActivityGPS(){
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }
    public void abrirMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void buscarNoticia(){
        l = linkP1;
        DownloadTask downloadTask = new DownloadTask();
        try {

            JSONObject jsonObject = new JSONObject(downloadTask.execute(l).get());

            JSONArray jsonArray = new JSONArray(jsonObject.getString("articles"));

            for(int i = 0; i < 20;i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String titulo = jsonObject1.getString("title");
                String url = jsonObject1.getString("url");

                news.add(new News(titulo, url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter = new NewsAdapter(this, R.layout.newslistview,news);
        gridView3.setAdapter(adapter);
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                page = news.get(i).getLink();
                browser();
            }
        });


    }

    public void browser(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(page));
        startActivity(browserIntent);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                int n=0;
                while (data != -1) {
                    if (n > 6) {
                        char current = (char) data;
                        result += current;
                        data = inputStreamReader.read();
                    } else {
                        n++;
                    }
                }
                Log.i("info",result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                Log.i("jsonObject", result);



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
