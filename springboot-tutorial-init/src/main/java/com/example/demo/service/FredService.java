package com.example.demo.service;

import com.example.demo.dto.Observation;
import com.example.demo.entity.FredData;
import com.example.demo.repository.FredRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FredService {

    final String baseUrl = "https://api.stlouisfed.org";

    @Value("${fred.apikey}")
    String fredApiKey;

    // Thread-safe Values
    final public static ParameterizedTypeReference<Map<String, Object>> mapTypeReference = new ParameterizedTypeReference<>() {
    };
    final public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    final FredRepository fredRepository;

    @Autowired
    public FredService(FredRepository fredRepository) {
        this.fredRepository = fredRepository;
    }

    private Double safeParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Throwable t) {
            return -999d;
        }
    }

    private Mono<Map<String, Object>> callUsbond10Y(String from, String to) {
        return WebClient.create(baseUrl)
                .method(HttpMethod.GET)
                .uri(it -> it.path("/fred/series/observations")
                        .queryParam("series_id", "DGS10")
                        .queryParam("units", "lin")
                        .queryParam("file_type", "json")
                        .queryParam("api_key", fredApiKey)
                        .queryParam("observation_start", from)
                        .queryParam("observation_end", to)
                        .build())
                .retrieve()
                .bodyToMono(mapTypeReference);
//                .bodyToMono(Map.class)
    }

    public Flux<Observation> getUsGovernmentBond10Y() {
        Flux<Observation> data = callUsbond10Y("2021-07-01", "2021-07-31")
                .flatMapMany(it -> {
                    List<Map<String, String>> list = (List<Map<String, String>>) it.get("observations");
                    return Flux.fromStream(
                            list.stream().map(m -> new Observation(m.get("date"), safeParseDouble(m.get("value"))))
                    );
                });
        return data;
    }

    //    @Scheduled(fixedDelay = 60000)
    public void updateFredDataRegularly() {
        log.info("Now updating freddata");
        String from = formatter.format(LocalDateTime.now().minusMonths(6));
        String to = formatter.format(LocalDateTime.now());
        Map<String, Object> data = callUsbond10Y(from, to).block(Duration.ofMinutes(5));
        List<Map<String, String>> list = (List<Map<String, String>>) data.get("observations");
        fredRepository.saveAll(
                list.stream()
                        .map(m -> new FredData("DGS10", m.get("date"), safeParseDouble(m.get("value"))))
                        .collect(Collectors.toList())
        );
    }

    public List<Observation> getStoredFredData(String seriesId, String from, String to) {
        return fredRepository.findAllBySeriesIdAndObservationDateAfterAndObservationDateBefore(seriesId, from, to)
                .stream().map(Observation::fromFredData).collect(Collectors.toList());
//                .map(Observation::fromFredData) == .map(fredDataEntity -> Observation.fromFredData(fredDataEntity))

    }

//    private Mono<Map<String,Object>> callIsbond10y(String from, String to) {
//        return WebClient.create(baseUrl)
//                .method(HttpMethod.GET)
//                .uri(it -> it.path("/fred/series/observations")
//                        .queryParam("series_id", "DGS10")
//                        .queryParam("units", "lin")
//                        .queryParam("file_type", "json")
//                        .queryParam("api_key", fredApiKey)
//                        .queryParam("observation_start", "2020-12-01")
//                        .queryParam("observation_end", "2020-12-31")
//                        .build())
//                .retrieve()
//                .bodyToMono(mapTypeReference);
//    }

    // 데이터를 list로 흘린다.
//    public Flux<Observation> getUsGovermentBond10Y() {
//        Flux<Observation> data = WebClient.create(BaseUrl)
//                .method(HttpMethod.GET)
//                .uri(it -> it.path("/fred/series/observations")
//                    .queryParam("series_id", "DGS10")
//                    .queryParam("units", "lin")
//                    .queryParam("file_type", "json")
//                    .queryParam("api_key", fredApiKey)
//                    .queryParam("observation_start", "2020-12-01")
//                    .queryParam("observation_end", "2020-12-31")
//                    .build())
//                .retrieve()
//                .bodyToMono(Map.class)
//                .flatMapMany(it ->{
//                    List<Map<String,String>> list = (List<Map<String, String>>) it.get("observations");
//                    return Flux.fromStream(
//                            list.stream().map(m -> new Observation(m.get("date"), safeParseDouble(m.get("value"))))
//                    );
//                });
//        return data;
//    }

//    public Flux<Observation> getUsGovermentBond10Y() {
//        Flux<Observation> data = callIsbond10y("2021-07-01", "2021-07-31")
//                .flatMapMany(it ->{
//                    List<Map<String,String>> list = (List<Map<String, String>>) it.get("observations");
//                    return Flux.fromStream(
//                            list.stream().map(m-> new Observation(m.get("date"), safeParseDouble(m.get("value"))))
//                    );
//                });
//        return data;
//    }

//    @Scheduled(fixedDelay = 5000)
//    public void logSometimes() {
//        log.error("I love errors");
//    }

//    @Scheduled(fixedDelay = 60000)
//    public void updateFredDataRegularly() {
//        log.info("Now updating freddata");
//        String from = formatter.format(LocalDateTime.now().minusMonths(3));
//        String to = formatter.format(LocalDateTime.now());
//        Map<String,Object> data = (Map<String, Object>) callIsbond10y(from, to).block(Duration.ofMinutes(5));
//        List<Map<String,String>> list = (List<Map<String, String>>) data.get("observations");
//        fredRepository.saveAll(
//                list.stream()
//                .map(m -> new FredData("DGS10", m.get("data"), safeParseDouble(m.get("value"))))
//                .collect(Collectors.toList()));
//    }
//
//    public List<Observation> getStoredFredData(String seriesId, String from, String to) {
//        return fredRepository.findBySeriesIdAndObservationDateAfterAndObservationDateBefore(seriesId, from, to)
//                .stream().map(Observation::fromFredData).collect(Collectors.toList());
//    }
}
