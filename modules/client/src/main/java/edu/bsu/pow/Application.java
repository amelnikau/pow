package edu.bsu.pow;

import edu.bsu.pow.model.Puzzle;
import edu.bsu.pow.model.Solution;
import edu.bsu.pow.service.HashingService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner
{
    @Value("${pow.server.url}")
    private String serverUrl;

    private static final String GET_PUZZLE_ENDPOINT = "/getPuzzle";
    private static final String REVOKE_CERTIFICATE_ENDPOINT = "/revokeCertificate";

    private static final String REVOKE_CERTIFICATE_BODY = "bodyContent";


    @Override
    public void run(String... args) throws InterruptedException
    {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Client sends request to get puzzle");
        Puzzle puzzle = restTemplate.getForObject(serverUrl + GET_PUZZLE_ENDPOINT, Puzzle.class);
        System.out.println(String.format("Response with puzzle received: %s", puzzle));
        System.out.println("Calculation of solution for given puzzle...");
        HashingService hashingService = new HashingService();
        Solution solution = hashingService.getSolution(puzzle.getPuzzle(), puzzle.getD(), REVOKE_CERTIFICATE_BODY);
        System.out.println(String.format("For puzzle: %s and d=%s solution is \n%s", puzzle.getPuzzle(), puzzle.getD(), solution));


        HttpHeaders headers = new HttpHeaders();
        headers.set("nonce", String.valueOf(solution.getNonce()));
        headers.set("solution", solution.getSolution());
        headers.set("puzzle", puzzle.getPuzzle());
        headers.set("d", String.valueOf(puzzle.getD()));
        HttpEntity<String> entity = new HttpEntity<>(REVOKE_CERTIFICATE_BODY, headers);
        System.out.println("Sending solution with nonce to revoke certificate endpoint");

        String response = restTemplate.postForObject(serverUrl + REVOKE_CERTIFICATE_ENDPOINT, entity, String.class);
        System.out.println(String.format("Response for revoking certificate received: %s", response));
    }

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
    }

}