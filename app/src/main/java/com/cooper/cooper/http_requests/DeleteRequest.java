package com.cooper.cooper.http_requests;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marco on 23/02/2018.
 */

public class DeleteRequest extends AsyncTask<String, String, JSONObject> {

    private JSONObject getData;
    static final String COOKIES_HEADER = "Set-Cookie";
    static android.webkit.CookieManager CookieManager = android.webkit.CookieManager.getInstance();
    private HTTPRequestListener listener;
    private int statusCode;
    public DeleteRequest() {
    }
    public DeleteRequest(JSONObject getData) {
        this.getData = getData;
    }
    public DeleteRequest(HTTPRequestListener listener) {
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
            urlConnection.setRequestMethod("DELETE");
            urlConnection.connect();
            this.statusCode = urlConnection.getResponseCode();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                return new JSONObject();
            }

            JSONObject response = new JSONObject();
            response.put("status_code", statusCode);
            response.put("response", buffer.toString());

            return response;

        } catch (Exception e) {
            Log.d("Error", e.toString());
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return new JSONObject();
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
