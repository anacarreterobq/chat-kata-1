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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.ChatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;


public class ServerSender extends AsyncTask<String,Void, String> {

    private ChatActivity chatActivity;

    public ServerSender(ChatActivity ca){
        chatActivity=ca;
    }


    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... url) {
        //Descargamos el fichero

        InputStream is = null;
        String result = "";
        JSONObject json = null;
        String respStr="";

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url[0]);
            String msg=url[1];
            httppost.setHeader("content-type", "application/json");

            try{
                StringEntity entity = new StringEntity(msg);
                httppost.setEntity(entity);
                HttpResponse resp = httpclient.execute(httppost);
                respStr=resp.getStatusLine().getReasonPhrase();

            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //if no OK

        return respStr;
    }

    //protected void onProgressUpdate (Float... valores) {

    //}

    protected void onPostExecute(JSONObject msg) {

    }

}
