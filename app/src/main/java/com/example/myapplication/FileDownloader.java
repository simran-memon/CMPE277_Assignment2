package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader extends AsyncTask<String, Integer, String> {
    private String TagName = "File Downloader";

    @Override
    protected String doInBackground(String... sUrl) {
        Log.e(TagName, "Start the file download progress" + sUrl[0]);
       
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            
            
            Log.e(TagName, String.valueOf(connection.getResponseCode()));
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            
            int fileLength = connection.getContentLength();
            Log.e(TagName, String.valueOf(fileLength) );
            
            input = connection.getInputStream();

            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/Download/");
            boolean value = folder.mkdir();
            String fileName = sUrl[0].substring(sUrl[0].lastIndexOf('/') + 1);
            String fullNAme = folder.getAbsolutePath() + "/" + fileName;
            File documentFile = new File(fullNAme);
            if(!documentFile.exists()){
                try {
                    documentFile.createNewFile();
                } catch (Exception e) {
                    Log.e(TagName, e.toString());
                    e.printStackTrace();
                }
            }



            output = new FileOutputStream(documentFile, false);

            byte data[] = new byte[4096];
            long total = 0;
            int count;

            while ((count = input.read(data)) != -1) {
                
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));

                output.write(data, 0, count);
            }
        } catch (Exception e) {
            Log.e(TagName, e.toString());
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }

        Log.e(TagName, "Completed the file download " + sUrl[0]);
        return null;
    }
}
