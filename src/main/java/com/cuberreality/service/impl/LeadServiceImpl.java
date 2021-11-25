package com.cuberreality.service.impl;

import com.cuberreality.request.leads.CreateLeadRequest;
import com.cuberreality.request.leads.UpdateLeadRequest;
import com.cuberreality.response.leads.CreateLeadResponse;
import com.cuberreality.response.leads.LeadResponse;
import com.cuberreality.service.LeadService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class LeadServiceImpl implements LeadService {

    @Override
    public CreateLeadResponse createLead(CreateLeadRequest createLeadRequest) throws Exception {

        CreateLeadResponse createLeadResponse = postWithHeaders(createLeadRequest);

        return createLeadResponse;
    }

    public CreateLeadResponse postWithHeaders(CreateLeadRequest createLeadRequest) throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth("1000.c638ef0b29357324e00ed358013db1a7.ab7100ce48c870ef3722b72ccc79adc3");
//        for (String key : headers.keySet()) {
//            httpHeaders.add(key, headers.get(key));
        // }
        HttpEntity<CreateLeadRequest> entity = new HttpEntity<>(createLeadRequest, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateLeadResponse> response = restTemplate.postForEntity("https://www.zohoapis.in/bigin/v1/Deals", entity, CreateLeadResponse.class);
        System.out.println(response.getBody());

        return response.getBody();

    }

    private static void sendPOST() throws IOException {


//        URL obj = new URL("https://www.zohoapis.in/bigin/v1/Deals");
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
//
//        // For POST only - START
//        con.setDoOutput(true);
//        con.setRequestProperty("Authorization","Bearer Token "+"1000.ca01e84b2312a0c6e503f7ade6c3a2d3.7c9b711f095f2b1bd9024f4125ed70eb");
//        OutputStream os = con.getOutputStream();
//        //os.write(POST_PARAMS.getBytes());
//        os.flush();
//        os.close();
//        // For POST only - END
//
//
//        int responseCode = con.getResponseCode();
//        System.out.println("POST Response Code :: " + responseCode);
//
//        if (responseCode == HttpURLConnection.HTTP_OK) { //success
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // print result
//            System.out.println(response.toString());
//        } else {
//            System.out.println("POST request not worked");
//        }
    }

    @Override
    public LeadResponse getLead(String id) {
        return null;
    }

    @Override
    public List<LeadResponse> getLeads() {
        return null;
    }

    @Override
    public List<LeadResponse> updateLead(UpdateLeadRequest updateLeadRequest) {
        return null;
    }


}
