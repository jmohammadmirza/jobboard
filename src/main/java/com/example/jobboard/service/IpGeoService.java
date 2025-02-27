package com.example.jobboard.service;

import com.example.jobboard.pojo.IpGeoData;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class IpGeoService {

    private static final String API_KEY = "9078fc9d5716466db2bb5bd6311d0d81";

    @Autowired
    private com.example.jobboard.repository.IpGeoDataRepository ipGeoDataRepository;

    public String saveIpGeoData(String ipAddress) throws IOException {

        IpGeoData ipGeoData=new IpGeoData();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
      
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.ipgeolocation.io/ipgeo").newBuilder();
        urlBuilder.addQueryParameter("apiKey", API_KEY);
        urlBuilder.addQueryParameter("ip", ipAddress);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response response = call.execute();

        if (response.isSuccessful()) {
            String responseBody = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();
            ipGeoData = objectMapper.readValue(responseBody, IpGeoData.class);

            ipGeoDataRepository.save(ipGeoData);
        } else {
            // Handle error response
            System.err.println("API request failed: " + response.code());
        }
        return buildLocationString(ipGeoData);
    }


    public String buildLocationString(IpGeoData ipGeoData) {
        StringBuilder location = new StringBuilder();
    
        if (ipGeoData != null) {
            if (ipGeoData.getCity() != null && !ipGeoData.getCity().isEmpty()) {
                location.append(ipGeoData.getCity()).append(", ");
            }
    
            if (ipGeoData.getState_prov() != null && !ipGeoData.getState_prov().isEmpty()) {
                location.append(ipGeoData.getState_prov()).append(", ");
            }
    
            if (ipGeoData.getCountry_name_official() != null && !ipGeoData.getCountry_name_official().isEmpty()) {
                location.append(ipGeoData.getCountry_name_official());
            }
    
            // Remove trailing comma and space if present
            if (location.length() > 2 && location.substring(location.length() - 2).equals(", ")) {
                location.delete(location.length() - 2, location.length());
            }
        }
        return location.toString();
    }
}
