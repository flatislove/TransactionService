package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.entity.enums.CurrencyEnum;
import com.flvtisv.testsolva.service.CurrencyService;
import com.flvtisv.testsolva.service.TwelveCurrencyService;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class TwelveCurrencyServiceImpl implements TwelveCurrencyService {

    private String key = "876a39378ce14a2393c8977f1127f025";
    private final CurrencyService service;

    public TwelveCurrencyServiceImpl(CurrencyService service) {
        this.service = service;
    }

    @SneakyThrows
    @Scheduled(cron = "@daily")
    public void getRatioUsdKzt() {
        URL url = new URL("https://api.twelvedata.com/time_series?symbol=USD/KZT&interval=1day&apikey=" + key);
        connect(url);
    }

    @SneakyThrows
    @Scheduled(cron = "@daily")
    public void getRatioUsdRub() {
        URL url = new URL("https://api.twelvedata.com/time_series?symbol=USD/RUB&interval=1day&apikey=" + key);
        connect(url);
    }

    private void connect(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject json = new JSONObject(response.toString());
        JSONObject curName = (JSONObject) json.get("meta");
        String name = curName.getString("symbol");
        JSONObject object = (JSONObject) json.getJSONArray("values").get(0);
        BigDecimal value = object.getBigDecimal("close");
        String data = object.getString("datetime");
        Currency currency = new Currency(name, value, data);
        service.save(currency);
    }

    public BigDecimal getSumOfUsd(String symbolFrom, String symbolTo, BigDecimal sum, BigDecimal ratio) {
        StringBuilder currencies = new StringBuilder(symbolTo + "/" + symbolFrom);
        if (Objects.equals(currencies.toString(), CurrencyEnum.USD.name() + "/" + CurrencyEnum.KZT.name())) {
            return sum.divide(ratio, 4, RoundingMode.HALF_DOWN);
        } else if (Objects.equals(currencies.toString(), CurrencyEnum.USD.name() + "/" + CurrencyEnum.RUB.name())) {
            return sum.divide(ratio, 4, RoundingMode.HALF_DOWN);
        } else if (Objects.equals(currencies.toString(), CurrencyEnum.USD.name() + "/" + CurrencyEnum.USD.name())) {
            return sum.setScale(4,RoundingMode.HALF_DOWN);
        } else {
            return BigDecimal.valueOf(-1);
        }
    }
}
