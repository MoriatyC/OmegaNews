package com.cmh.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class Crawler {
    //http://3g.163.com/touch/reconstruct/article/list/BAI6RHDKwangning/0-10.html
    @SuppressWarnings("finally")
    public String netEase(String url) {
        String content = null;
        try (   CloseableHttpClient client = HttpClients.createDefault(); ) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                content = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            return content;
        }
    }
    public void washData(String content) {
        
    }
}
