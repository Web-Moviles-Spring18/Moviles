package com.cooper.cooper.http_requests;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.cooper.cooper.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by marco on 23/02/2018.
 */

public class GetRequests extends AsyncTask<String, String, JSONObject> {

    private JSONObject getData;
    static final String COOKIES_HEADER = "Set-Cookie";
    static android.webkit.CookieManager CookieManager = android.webkit.CookieManager.getInstance();
    private HTTPRequestListener listener;
    private int statusCode;
    public GetRequests() {
    }
    public GetRequests(JSONObject getData) {
        this.getData = getData;
    }
    public GetRequests(HTTPRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String cookie_str = CookieManager.getCookie(strings[0]);
            if (CookieManager.hasCookies() && cookie_str != null) {
                // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                //Log.d("Cookie", TextUtils.join(";",  CookieManager.getCookieStore().getCookies()));
                urlConnection.setRequestProperty("Cookie", cookie_str);
            }
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            this.statusCode = urlConnection.getResponseCode();
            BufferedReader reader;
            StringBuilder buffer;

            if (statusCode ==  200) {
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.length() == 0) {
                    //return new JSONObject("{'response':'Error'}");
                    buffer.append("No Response Founded!");
                }
            } else {
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getErrorStream();
                buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.length() == 0) {
                    //return new JSONObject("{'response':'Error'}");
                    buffer.append("No Response Founded!");
                }
            }



            JSONObject response = new JSONObject();
            response.put("status_code", statusCode);
            response.put("response", buffer.toString());

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if(this.listener != null) {
            this.listener.requestDone(jsonObject, this.statusCode);
        }
    }
}
