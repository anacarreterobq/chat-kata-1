package org.ejmc.android.simplechat.model;

/**
 * Created with IntelliJ IDEA.
 * User: laura
 * Date: 27/11/13
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ejmc.android.simplechat.ChatActivity;
import java.io.*;


public class ServerSender extends AsyncTask<String,Void, String> {

    private ChatActivity chatActivity;

    public ServerSender(ChatActivity ca){
        chatActivity=ca;
    }

    @Override
    protected String doInBackground(String... url) {

        String respStr="";
        HttpClient httpclient = new DefaultHttpClient();;
        HttpPost httppost = new HttpPost(url[0]);
        String msg = url[1];
        httppost.setHeader("content-type", "application/json");
        StringEntity entity;
        HttpResponse resp;

        if(!msg.startsWith("{\"message\":\"\"",0)) {
            do{
                try{
                    entity = new StringEntity(msg);
                    httppost.setEntity(entity);
                    resp = httpclient.execute(httppost);
                    respStr=resp.getStatusLine().getReasonPhrase();


                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }while(!respStr.equals("OK"));
        }

        return respStr;
    }

    @Override
    protected void onPostExecute(String n) {
        chatActivity.refresh_send();
    }

}
