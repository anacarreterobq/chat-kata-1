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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.ChatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
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

        String result = null;
        JSONObject json = null;

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url[0]);
            //httpget.getParams().setIntParameter("next_seq", Integer.parseInt(url[1]));
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            result= EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            json = new JSONObject(result);
        }catch(JSONException e){}

        return json;
    }

    //protected void onProgressUpdate (Float... valores) {

    //}

    @Override
    protected void onPostExecute(JSONObject msg) {
        chatActivity.refresh_msg(msg);
    }

}
