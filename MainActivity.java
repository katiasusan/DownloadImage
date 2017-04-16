package com.example.manwest.imagendownload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImg;
    ProgressBar mProgressBar;
    Boolean result;
    private Button btn1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        downloadedImg = (ImageView) findViewById(R.id.imageView);
        btn1=(Button)findViewById(R.id.button3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"mensaje",Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void downloadImage(View view){


        try {
                MiTareaAsincrona tarea= new MiTareaAsincrona();
            tarea.execute();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        Log.i("Botton: ","Tapped");


    }


    public void descarga(){
        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;

        try {
            myImage = task.execute("http://wallpaper-gallery.net/images/best-of-wallpapers-for-desktop/best-of-wallpapers-for-desktop-21.jpg").get();
            downloadedImg.setImageBitmap(myImage);
        }catch (Exception e){



        }


    }


    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {

            try{
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                publishProgress();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }


            return null;
        }
    }


    private void tareaLarga() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {
        boolean cancelado;
        Boolean result;


        @Override
        protected Boolean doInBackground(Void... params) {
            for (int i = 1; i <= 10; i++) {

                publishProgress(i * 10);
                if (isCancelled())
                    break;

            }


            return true;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
            mProgressBar.setProgress(progreso);

        }



        @Override
        protected void onPreExecute() {
            mProgressBar.setMax(100);
            mProgressBar.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
               descarga();
                Toast.makeText(getApplicationContext(), "Tarea finalizada!",
                        Toast.LENGTH_SHORT).show();


            }
        }

        @Override
        protected void onCancelled(Boolean result) {
            if (!result) {
                Toast.makeText(getApplicationContext(), "Tarea cancelada!",
                        Toast.LENGTH_SHORT).show();

            }
        }

    }





}
