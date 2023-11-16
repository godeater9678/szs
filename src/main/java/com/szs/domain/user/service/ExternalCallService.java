package com.szs.domain.user.service;

import com.szs.domain.user.dto.scrap.ScrapResponse;
import com.szs.domain.user.dto.UserDto;
import com.szs.domain.user.exception.SzsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class ExternalCallService {
    @Value("${external.scrap.url}")
    private String API_URL;
    private static final int MAX_ATTEMPTS = 3;//외부호출 재시도 카운트
    private static final int TIMEOUT_SECONDS = 3;//외부호출 타임아웃
    private static final Duration CIRCUIT_BREAKER_RESET_PERIOD = Duration.ofMinutes(1);//서킷브레이크 리셋 주기

    private static final RestTemplate restTemplate = new RestTemplate();

    //외부호출 장애 서킷브레이커
    private final CircuitBreaker circuitBreaker = new CircuitBreaker(MAX_ATTEMPTS, CIRCUIT_BREAKER_RESET_PERIOD);

    public ScrapResponse scrap(UserDto userDto) throws Exception {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            if (circuitBreaker.isCircuitOpen()) {
                System.out.println("외부서버 장애에 의한 서비스 일시 중지됐습니다. 테스트 진행을 위한 더미 데이터를 반환합니다.");
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(sampleJson, ScrapResponse.class);
            }

            try {
                return makeApiCall(userDto);
            } catch (Exception e){
                System.out.println(e.getMessage());
                circuitBreaker.recordFailure();
                if (circuitBreaker.isThresholdReached()) {
                    System.out.println("외부서버 장애에 의한 서비스 일시 중지됐습니다. 테스트 진행을 위한 더미 데이터를 반환합니다.");
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(sampleJson, ScrapResponse.class);
                }
            }

            try {
                TimeUnit.SECONDS.sleep(TIMEOUT_SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        throw new SzsException("요청을 처리할 수 없습니다.");
    }

    private  ScrapResponse makeApiCall(UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDto> request = new HttpEntity<UserDto>(userDto, headers);

        ResponseEntity<ScrapResponse> response = restTemplate.postForEntity(API_URL, request, ScrapResponse.class);

        return response.getBody();
    }

    private static class CircuitBreaker {
        private int failureCount = 0;
        private final int maxAttempts;
        private final Duration resetPeriod;
        private long lastFailureTimestamp = 0;

        CircuitBreaker(int maxAttempts, Duration resetPeriod) {
            this.maxAttempts = maxAttempts;
            this.resetPeriod = resetPeriod;
        }

        void recordFailure() {
            failureCount++;
            lastFailureTimestamp = System.currentTimeMillis();
        }

        boolean isThresholdReached() {
            return failureCount >= maxAttempts;
        }

        boolean isCircuitOpen() {
            if (failureCount >= maxAttempts) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastFailureTimestamp >= resetPeriod.toMillis()) {
                    reset();
                }
                return true;
            }
            return false;
        }

        void reset() {
            failureCount = 0;
            lastFailureTimestamp = 0;
        }
    }

    private final String sampleJson  = "{\n" +
            "    \"status\": \"success\",\n" +
            "    \"data\": {\n" +
            "        \"jsonList\": {\n" +
            "            \"급여\": [\n" +
            "                {\n" +
            "                    \"소득내역\": \"급여\",\n" +
            "                    \"총지급액\": \"30,000,000\",\n" +
            "                    \"업무시작일\": \"2020.10.02\",\n" +
            "                    \"기업명\": \"(주)활빈당\",\n" +
            "                    \"이름\": \"홍길동\",\n" +
            "                    \"지급일\": \"2020.11.02\",\n" +
            "                    \"업무종료일\": \"2021.11.02\",\n" +
            "                    \"주민등록번호\": \"860824-1655068\",\n" +
            "                    \"소득구분\": \"근로소득(연간)\",\n" +
            "                    \"사업자등록번호\": \"012-34-56789\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"산출세액\": \"600,000\",\n" +
            "            \"소득공제\": [\n" +
            "                {\n" +
            "                    \"금액\": \"100,000\",\n" +
            "                    \"소득구분\": \"보험료\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"금액\": \"200,000\",\n" +
            "                    \"소득구분\": \"교육비\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"금액\": \"150,000\",\n" +
            "                    \"소득구분\": \"기부금\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"금액\": \"700,000\",\n" +
            "                    \"소득구분\": \"의료비\"\n" +
            "                }\n" +
            "                ,{\n" +
            "                    \"총납임금액\": \"1,333,333.333\",\n" +
            "                    \"소득구분\": \"퇴직연금\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        \"appVer\": \"2021112501\",\n" +
            "        \"errMsg\": \"외부 통신오류에 따른 더미데이터입니다. 실제 서비스에서는 장애가 나지 않도록 빈 데이터를 반환해야합니다.\",\n" +
            "        \"company\": \"삼쩜삼\",\n" +
            "        \"svcCd\": \"test01\",\n" +
            "        \"hostNm\": \"jobis-codetest\",\n" +
            "        \"workerResDt\": \"2022-08-16T06:27:35.160789\",\n" +
            "        \"workerReqDt\": \"2022-08-16T06:27:35.160851\"\n" +
            "    },\n" +
            "    \"errors\": \"외부 통신오류에 따른 더미데이터입니다. 실제 서비스에서는 장애가 나지 않도록 빈 데이터를 반환해야합니다.\" \n" +
            "}";
}

