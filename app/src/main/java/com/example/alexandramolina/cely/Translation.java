package com.example.alexandramolina.cely;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.concurrent.ExecutionException;


public class Translation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ArrayList<String> textos=new ArrayList<>();
    private DownloadTask dt;
    private TranslationTask tt;
    private Button btn_traducir;
    private EditText txt_link;
    private Spinner spinner;
    private ProgressBar progressBar;
    private String idioma ="spa";
    private EditText link;
    private ArrayList<String> tipos = new ArrayList<>();
    private ArrayList<String> imagenes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transalation);


        btn_traducir = findViewById(R.id.btnTraducir);
        txt_link = findViewById(R.id.link);
        spinner = findViewById(R.id.spinner);


        link = findViewById(R.id.link);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Idiomas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
                String text = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT);
                switch (position) {
                    case 0:
                        idioma = "spa";
                        break;
                    case 1:
                        idioma = "en";
                        break;
                    case 2:
                        idioma = "pt";
                        break;
                    case 3:
                        idioma = "it";
                        break;
                    case 4:
                        idioma = "fr";
                        break;
                    case 5:
                        idioma = "de";
                        break;
                    case 6:
                        idioma = "ru";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void abrirActivityTraductor(){
        Intent intent = new Intent(Translation.this, NoticiaTraducidaActivity.class);
        intent.putExtra("texto",textos);
        intent.putExtra("tipo",tipos);
        startActivity(intent);
    }


    public void translate(View view){
        traducir();
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
        int id=item.getItemId();
        return false;
    }

    public void traducir(){

        dt= new DownloadTask();
        tt=new TranslationTask();
        Document document=null;
        Elements nombres=null;
        try {


            String html= dt.execute(link.getText().toString()).get();

            document = Jsoup.parse(html);
            nombres = document.select("div.story-body h1, div.story-body__inner p, div.story-body__inner ul li, div.story-body__inner h2, div.story-body__inner figure span img");

            for (int i = 0; i < nombres.size(); i++) {

                if(nombres.get(i).tagName() == "figure"){

                    textos.add("");

                    if(nombres.get(i).tagName() == "img"){
                        imagenes.add(nombres.get(i).attr("js-image-replace"));
                    }

                }

                else {
                    textos.add(nombres.get(i).text());
                }
                tipos.add(nombres.get(i).tagName());
                //Log.i("Nombres:", nombres.get(i).tagName());

            }

            textos= tt.execute(textos).get();

            //for(String s:tipos){
            //    Log.d("TIPO:",s);
            //}

            abrirActivityTraductor();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class TranslationTask extends AsyncTask<ArrayList<String>, Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... urls) {

            ArrayList<String> a=urls[0];
            ArrayList<String> salida=new ArrayList<>();
            for(String s:a){
                if(s != "") {
                    TranslateOptions options = TranslateOptions.newBuilder()
                            .setApiKey("AIzaSyBArteM1ejf2C6fCBZojg0JZkD7ZZ9IiQk")
                            .build();
                    Translate translate = options.getService();
                    final com.google.cloud.translate.Translation translation =
                            translate.translate(s,
                                    Translate.TranslateOption.targetLanguage(idioma));
                    salida.add(translation.getTranslatedText());
                }
            }
            //for(String s:salida){
            //    Log.d("PRUEBA",s);
            //}
            return salida;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            Toast.makeText(getApplicationContext(),"Ha terminado",Toast.LENGTH_LONG);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            String input;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();


                while ((input = reader.readLine()) != null) {
                    stringBuilder.append("\n");
                    stringBuilder.append(input);
                }

                reader.close();
                inputStreamReader.close();

                result = stringBuilder.toString();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}
