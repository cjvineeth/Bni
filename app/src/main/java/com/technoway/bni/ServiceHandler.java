package com.technoway.bni;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
                
                
                httpGet.addHeader(BasicScheme.authenticate(
                		 new UsernamePasswordCredentials("admin", "admin123"),
                		 "UTF-8", false));

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }




    public  String PostBody(String url, JSONObject object){


        HttpResponse resp;
        HttpEntity httpEntity = null;

        String body_response="";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);


        HttpClient hc = new DefaultHttpClient();
        String message;

        HttpPost p = new HttpPost(url);


        try {
            message = object.toString();


            p.setEntity(new StringEntity(message, "UTF8"));
            p.setHeader("Content-type", "application/json");
            p.addHeader(BasicScheme.authenticate(
           		 new UsernamePasswordCredentials("admin", "admin123"),
           		 "UTF-8", false));
            
            resp= hc.execute(p);

            httpEntity = resp.getEntity();
            body_response = EntityUtils.toString(httpEntity);

            Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();

        }



        return  body_response;

    }








    public  String PostJsonArray(String url, JSONArray array){


        HttpResponse resp;
        HttpEntity httpEntity = null;

        String body_response="";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);


        HttpClient hc = new DefaultHttpClient();
        String message;

        HttpPost p = new HttpPost(url);


        try {
            message = array.toString();


            p.setEntity(new StringEntity(message, "UTF8"));
            p.setHeader("Content-type", "application/json");
            p.addHeader(BasicScheme.authenticate(
                    new UsernamePasswordCredentials("admin", "admin123"),
                    "UTF-8", false));

            resp= hc.execute(p);

            httpEntity = resp.getEntity();
            body_response = EntityUtils.toString(httpEntity);

            Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();

        }



        return  body_response;

    }








}