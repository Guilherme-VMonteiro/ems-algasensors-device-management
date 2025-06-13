package com.algaworks.algasensors.device.management.api.client;

import com.algaworks.algasensors.device.management.api.client.exceptions.SensorMonitoringClientBadGatewayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

	private final RestClient.Builder builder;
	
	private ClientHttpRequestFactory generateClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		
		factory.setReadTimeout(Duration.ofSeconds(5));
		factory.setConnectTimeout(Duration.ofSeconds(3));
		
		return factory;
	}
	
	public RestClient temperatureMonitoringRestClient() {
		return builder.baseUrl("http://localhost:8082")
				.requestFactory(generateClientHttpRequestFactory())
				.defaultStatusHandler(HttpStatusCode::isError, (req, res) -> {
					throw new SensorMonitoringClientBadGatewayException();
				})
				.build();
	}
}
