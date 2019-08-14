package com.shell;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SalesforceUtil {
    private static String OAUTH_TOKEN = "00D2v000001eexa!ARQAQNMSIWNW_EEkt7Eg6ivaOkCPO.2OgS4vRd1qbNQ7cryVuPJCLogF.Ufp96.jzT8_ARzreS5tER0KiAdPLE7RSpXUODMO";
    private static String BASE_URL = "https://ap15.salesforce.com/services/data/v46.0";
    private static BasicHeader Header = new BasicHeader("Authorization: ", "Bearer "+OAUTH_TOKEN);

    //define important fields
    private static String OPPORTUNITY_OBJECT_NAME = "reejh__Opportunity__c";
    private static String CUSTOM_ACOUNT_ID = "reejh__AccountId__c";

    public static String getOauthToken() {
        return OAUTH_TOKEN;
    }

    public static void setOauthToken(String oauthToken) {
        OAUTH_TOKEN = oauthToken;
    }

    public static String findOpportunityByAccountId(String AccountId){
        String resource = "/sobjects/"+OPPORTUNITY_OBJECT_NAME+"/"+CUSTOM_ACOUNT_ID+"/"+AccountId;
        return MakeRequest(resource);
    }

    public static String GetAccountIds(){
        String resource = "/query" + "?q=select+reejh__AccountId__c+from+reejh__Opportunity__c";
        return MakeRequest(resource);
    }

    public static String getUsersGreaterThanXProbability(String x){
        try {
            String query = "select reejh__AccountId__c from reejh__Opportunity__c where reejh__Probability__c > " + x;
            String resource = "/query?q=" + URLEncoder.encode(query, "UTF-8");
            return MakeRequest(resource);
        }
        catch (UnsupportedEncodingException ue){
            return "";
        }
    }

    public static String getMaxRevenuePerBusiness(){
        try {
            String query = "select reejh__Type__c,MAX(reejh__ExpectedRevenue__c)  from reejh__Opportunity__c group by reejh__Type__c";
            String resource = "/query?q=" + URLEncoder.encode(query, "UTF-8");
            return MakeRequest(resource);
        }
        catch (UnsupportedEncodingException ue){
            return "";
        }
    }

    public static String getAverageRevenuePerFiscalYear(){
        try {
            String query = "select reejh__FiscalYear__c,AVG(reejh__ExpectedRevenue__c) from reejh__opportunity__c group by reejh__FiscalYear__c";
            String resource = "/query?q=" + URLEncoder.encode(query, "UTF-8");
            return MakeRequest(resource);
        }
        catch (UnsupportedEncodingException ue){
            return "";
        }
    }

    public static String getAvgProbabilityPerBusinessType(){
        try {
            String query = "select reejh__AccountId__c, reejh__Amount__c from reejh__Opportunity__c order by reejh__Amount__c desc limit 1";
            String resource = "/query?q=" + URLEncoder.encode(query, "UTF-8");
            return MakeRequest(resource);
        }
        catch (UnsupportedEncodingException ue){
            return "";
        }
    }

    public static String getAccountIdWithMaxAmount(){
        try {
            String query = "select reejh__Type__c, AVG(reejh__Probability__c) from reejh__Opportunity__c group by reejh__Type__c";
            String resource = "/query?q=" + URLEncoder.encode(query, "UTF-8");
            return MakeRequest(resource);
        }
        catch (UnsupportedEncodingException ue){
            return "";
        }
    }

    public static String MakeRequest(String ReqResource) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            String uri = BASE_URL+ ReqResource;
            System.out.println(uri);
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader(Header);

            HttpResponse response = httpClient.execute(httpGet);
            String response_string = EntityUtils.toString(response.getEntity());

            System.out.println(response_string);

            return response_string;
        }
        catch (IOException io){
            System.out.println("Exception IO");
            io.printStackTrace();
            return "";
        }
        catch (NullPointerException np){
            System.out.println("NPE Exception");
            np.printStackTrace();
            return "";
        }
    }
}
