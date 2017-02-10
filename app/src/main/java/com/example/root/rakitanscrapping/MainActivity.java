package com.example.root.rakitanscrapping;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner1;
    private TextView tv1;

    String[] dataSpinner1 = new String[20];

    String url = "http://www.rakitan.com/simulasi.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner)findViewById(R.id.spinner);
        final List<String> list = new ArrayList<String>();
        list.add("== Intel LGA 1151 ==");

        final ArrayAdapter<String> dataAdapt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        dataAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapt);


        class title extends AsyncTask<Void,Void,Void>{

            String title;

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Document doc = Jsoup.connect(url).get();
                    title = doc.title();

                    // ambil data untuk spinner
                    Element selek1 = doc.select("select").get(1);
                    Elements selek1isi = selek1.select("option");

                    for(int i = 0 ; i < selek1isi.size() ; i++ ){
                        Element option = selek1isi.get(i);

                        dataSpinner1[i] = option.text();
//                    System.out.println(dataSpinner1[i]);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                tv1 = (TextView)findViewById(R.id.textView);
                tv1.setText(title);
                for ( int i = 0 ; i<10 ; i++ ){
                    list.add(dataSpinner1[i]);
                    System.out.println("ditabahkan data " + dataSpinner1[i]);
                }

                dataAdapt.notifyDataSetChanged();
            }
        } // end of title

        new title().execute();


    } // end of onCreate



}
