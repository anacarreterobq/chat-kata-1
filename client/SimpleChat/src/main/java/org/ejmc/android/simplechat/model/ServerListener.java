package org.ejmc.android.simplechat.model;

/**
 * Created with IntelliJ IDEA.
 * User: laura
 * Date: 27/11/13
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */

import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.ejmc.android.simplechat.ChatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



public class ServerListener extends AsyncTask<String,Void, JSONObject> {

    private ChatActivity chatActivity;

    public ServerListener(ChatActivity ca){
        chatActivity=ca;
    }


    protected void onPreExecute() {

    }
    @Override
    protected JSONObject doInBackground(String... url) {
        //Descargamos el fichero

        InputStream is = null;
        String result = "";
        JSONObject json = null;

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url[0]);
            httpget.getParams().setIntParameter("seq", Integer.parseInt(url[1]));
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            json = new JSONObject(result);
        }catch(JSONException e){}

        return json;
    }

    //protected void onProgressUpdate (Float... valores) {

    //}

    protected void onPostExecute(JSONObject msg) {
        chatActivity.refresh(msg);

    }

}