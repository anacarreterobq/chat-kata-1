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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.ChatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;



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
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            result= EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            if(result!=null)
                json = new JSONObject(result);
        }catch(JSONException e){}

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject msg) {
        if(msg!=null)
            chatActivity.refresh_msg(msg);
    }

}
