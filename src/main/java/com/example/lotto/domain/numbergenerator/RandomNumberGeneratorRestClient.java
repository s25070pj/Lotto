package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RandomNumberGeneratorRestClient implements RandomNumberGenerable {

   private final RestTemplate restTemplate;
   private final String uri;
   private final int port;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        String urlForService = getUrlForService("/api/v1.0/random");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .queryParam("min", 1)
                .queryParam("max", 99)
                .queryParam("count", 25)
                .toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Integer>>() {
                });
        List<Integer> numbers = response.getBody();
        System.out.println(numbers);




        return SixRandomNumbersDto.builder()
                .numbers(numbers.stream()
                        .collect(Collectors.toSet())).build();
    }



    private String getUrlForService(String service){
        return uri + ":" + port + service;
    }
}
