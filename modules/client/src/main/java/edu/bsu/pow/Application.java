package edu.bsu.pow;

import java.util.Collections;

import edu.bsu.pow.model.Puzzle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner
{
    @Value("${pow.server.url}")
    private String serverUrl;

    private static final String GET_PUZZLE_ENDPOINT = "/getPuzzle";
    private static final String REVOKE_CERTIFICATE_ENDPOINT = "/revokeCertificate";


    @Override
    public void run(String... args) throws InterruptedException
    {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Client sends request to get nonce for puzzle");
        Puzzle puzzle = restTemplate.getForObject(serverUrl + GET_PUZZLE_ENDPOINT, Puzzle.class);
        System.out.println(String.format("Response received: %s", puzzle));
        System.out.println("Imitating calculation of solution for given nonce");
        Thread.sleep(1000L);
        long solution = puzzle.getNonce() * 60;
        System.out.println(String.format("Solution is %s for nonce %s", solution, puzzle.getNonce()));


        HttpHeaders headers = new HttpHeaders();
        headers.set("nonce", String.valueOf(puzzle.getNonce()));
        headers.set("solution", String.valueOf(solution));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        System.out.println("Sending solution with nonce to revoke certificate endpoint");

        String response = restTemplate.postForObject(serverUrl + REVOKE_CERTIFICATE_ENDPOINT, entity, String.class);
        System.out.println(String.format("Response for revoking certificate received: %s", response));
    }

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
    }

}