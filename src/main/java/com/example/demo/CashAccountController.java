package com.example.demo;

import com.example.jsonObjects.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class CashAccountController {
    private final Logger logger = LoggerFactory.getLogger(CashAccountController.class);

    private final String domain = "https://sandbox.platfr.io";

    public String getCashBalance(String accountId) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "/api/gbs/banking/v4.0/accounts/" + accountId + "/balance ";

        String balance;

        HttpHeaders header = this.getHeader();
        HttpEntity<String> entity = new HttpEntity<>(header);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(domain +
                    uri, HttpMethod.GET, entity, String.class);

            logger.info(String.valueOf(response.getBody()));

            JSONObject jsonObject = new JSONObject(response.getBody());
            checkResponse(jsonObject);
            ObjectMapper mapper = new ObjectMapper();

            AccountBalance accountBalance = mapper.readValue(jsonObject.get("payload").toString(), AccountBalance.class);
            balance = accountBalance.getBalance();

        } catch (Exception e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return balance;
    }

    public String getCashAccountTransactions(String accountId, String fromAccountingDate, String toAccountingDate) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "/api/gbs/banking/v4.0/accounts/" + accountId +
                "/transactions?" + "fromAccountingDate=" + fromAccountingDate +
                "&toAccountingDate=" + toAccountingDate;

        String result;
        logger.info(uri);
        HttpHeaders header = getHeader();
        HttpEntity<String> entity = new HttpEntity<>(header);

        ResponseEntity<String> response;
        try {

            response = restTemplate.exchange(domain + uri, HttpMethod.GET, entity, String.class);

            logger.info(response.getBody());

            JSONObject jsonObject = new JSONObject(response.getBody());
            checkResponse(jsonObject);

            JSONArray list = jsonObject.getJSONObject("payload").getJSONArray("list");
            logger.info(list.toString());

            result = list.toString();

        } catch (Exception e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }

        return result;
    }

    public String createMoneyTransfer(String accountId, MoneyTransferPayload moneyTransferPayload) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "/api/gbs/banking/v4.0/accounts/" + accountId + "/payments/money-transfers";

        HttpHeaders header = getHeader();
        String timeZone = "Europe/Rome";
        header.add("X-Time-Zone", timeZone);
        String result;
        try {
            String jsonStr = (new Gson().toJson(moneyTransferPayload));
            logger.info(jsonStr);

            HttpEntity<String> entity = new HttpEntity<>(jsonStr, header);

            ResponseEntity<String> response = restTemplate.postForEntity(domain + uri, entity, String.class);

            logger.info("Response: " + response.getBody());
            JSONObject jsonObject = new JSONObject(response.getBody());

            checkResponse(jsonObject);

            result = jsonObject.get("payload").toString();
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }
        return result;
    }

    private HttpHeaders getHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String authSchema = "S2S";
        header.add("Auth-Schema", authSchema);
        String apiKey = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
        header.add("Api-Key", apiKey);
        return header;
    }

    private void checkResponse (JSONObject jsonObject) throws ResponseException{
        String status = jsonObject.getString("status");

        if (status.compareTo("KO") == 0) {
            throw new ResponseException(jsonObject.getString("error"));
        }
    }

}
