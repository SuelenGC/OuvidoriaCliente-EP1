package br.com.suelengc.ouvidoria.client.web;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WebClient {
    private final String url;

    public WebClient(String url) {
        this.url = url;
    }


    public String postHttps(Map<String, String> inParams) {
        String jsonResponse = "";
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : inParams.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            }, null);

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(this.url);

            request.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(request);

            jsonResponse = EntityUtils.toString(response.getEntity());
            Log.i("Ouvidoria", jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;

    }


    public String post(String json) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost post = new HttpPost(url);
            if (json != null) {
                post.setEntity(new StringEntity(json));
            }
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get() {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpGet get = new HttpGet(url);
            get.setHeader("Accept", "application/json");
            get.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(get);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
