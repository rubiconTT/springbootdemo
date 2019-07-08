package com.tt.util;

import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by TT on 2018/7/24.
 */
@Log4j2
public class HttpUtils {

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    public static String getRequest(String url,String authStr){

        if(!StringUtils.isNotEmpty(url)){
            return null;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response =null;
        InputStream ins=null;
        BufferedReader in=null;
        String result =null;
        HttpGet httpGet =null;

        try {
            httpGet = new HttpGet(url);
            if(StringUtils.isNotEmpty(authStr)){
                httpGet.addHeader(HttpHeaders.AUTHORIZATION, authStr);
            }

            response = httpclient.execute(httpGet);
//            log.info(response.getStatusLine());
            HttpEntity entity = response.getEntity();

            ins =entity.getContent();

            StringBuffer sb = new StringBuffer();

            in =new BufferedReader( new InputStreamReader(ins,"UTF-8") );
            String str = null;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
            result =sb.toString();
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ins!=null){
                    ins.close();
                }
                if(in!=null){
                    in.close();
                }
                if(response!=null){
                    response.close();
                }
                if(httpGet != null && !httpGet.isAborted()){
                    httpGet.releaseConnection();
                    httpGet.abort();
                }

                if(httpclient!=null){
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getRequest(String url){

        if(!StringUtils.isNotEmpty(url)){
            return null;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response =null;
        InputStream ins=null;
        BufferedReader in=null;
        String result =null;
        HttpGet httpGet =null;

        try {
            httpGet = new HttpGet(url);
            response = httpclient.execute(httpGet);
//            log.info(response.getStatusLine());
            HttpEntity entity = response.getEntity();

            ins =entity.getContent();

            StringBuffer sb = new StringBuffer();

            in =new BufferedReader( new InputStreamReader(ins,"UTF-8") );
            String str = null;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
            result =sb.toString();
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ins!=null){
                    ins.close();
                }
                if(in!=null){
                    in.close();
                }
                if(response!=null){
                    response.close();
                }
                httpGet.releaseConnection();
                if(httpclient!=null){
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String postRequest(String httpUrl, Map<String,String> headMap,Object paramsObj){

        if(paramsObj == null){
            return null;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HttpPost httpPost = new HttpPost(httpUrl);
        httpPost.setConfig(requestConfig);

        if(headMap !=null && !headMap.isEmpty() && headMap.size()>0){
            for(String headKey:headMap.keySet()){
                if(headKey.equals(HttpHeaders.AUTHORIZATION)){
                    httpPost.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION,headMap.get(headKey)));
                }
                httpPost.addHeader(headKey,headMap.get(headKey));
            }
        }

        String respContent =null;
        try {
            StringEntity se=setRequestEntity(paramsObj);

            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);
            if(response!=null ){
                HttpEntity entity = response.getEntity();
                log.info("status line: "+response.getStatusLine());
                if(response.getStatusLine().getStatusCode()==200){
                    respContent = EntityUtils.toString(entity, "UTF-8");
                }

            }else{
                log.error("http request failed!");
            }

            log.info("response content: "+respContent);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeHttpConnection(httpClient,response);
        }
        return respContent;
    }

    private static StringEntity setRequestEntity(Object paramsObj){
        StringEntity se=null;
        String paramObjType=GeneralUtils.getObjType(paramsObj);


            if(paramObjType.toUpperCase().equals("STRING")){
                //json string
                if(JSONObject.fromObject(paramsObj) instanceof JSONObject){
                    se=new StringEntity(paramsObj.toString(),"UTF-8");
                    se.setContentType("application/json;charset=UTF-8");
                }else {
                    //xml string
                    se=new StringEntity(paramsObj.toString(),
                            ContentType.create("text/xml", "UTF-8"));
                }
            }

            if(paramObjType.toUpperCase().contains("MAP")){
                JSONObject paramsJson=JSONObject.fromObject(paramsObj);
                se=new StringEntity(paramsJson.toString(),"UTF-8");
                se.setContentType("application/json;charset=UTF-8");

            }

            if(paramObjType.toUpperCase().contains("JSON")){
                se=new StringEntity(paramsObj.toString(),"UTF-8");
                se.setContentType("application/json;charset=UTF-8");
            }

        return se;
    }



    public static String postRequestByObjMap(String httpUrl,Map<String, Object> paramsMap){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HttpPost httpPost = new HttpPost(httpUrl);
        httpPost.setConfig(requestConfig);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if(paramsMap!=null&&!paramsMap.isEmpty()){
            for (String key : paramsMap.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, paramsMap.get(key).toString()));
            }
        }
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        String responseContent =null;
        try {
            StringEntity se=new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            log.info("status line: "+response.getStatusLine());
            responseContent = EntityUtils.toString(entity, "UTF-8");
            log.info("response content: "+responseContent);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeHttpConnection(httpClient,response);
        }
        return responseContent;
    }

    public static String postRequestByJson(String httpUrl,String jsonParams){

        if(jsonParams ==null
                || JSONObject.fromObject(jsonParams)==null
                || !(JSONObject.fromObject(jsonParams) instanceof JSONObject)
                || JSONObject.fromObject(jsonParams).isNullObject()){
            return null;
        }
        log.info(jsonParams.toString());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HttpPost httpPost = new HttpPost(httpUrl);
        httpPost.setConfig(requestConfig);

        String responseContent =null;
        try {
            StringEntity se=new StringEntity(jsonParams.toString(),"UTF-8");
            se.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(se);
            response = httpClient.execute(httpPost);
            if(response!=null && response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                log.info("status line: "+response.getStatusLine());
                responseContent = EntityUtils.toString(entity, "UTF-8");
            }

            log.info("response content: "+responseContent);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeHttpConnection(httpClient,response);
        }
        return responseContent;
    }



    private static void closeHttpConnection(CloseableHttpClient httpClient,CloseableHttpResponse response){
        try {
            // 关闭连接,释放资源
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
