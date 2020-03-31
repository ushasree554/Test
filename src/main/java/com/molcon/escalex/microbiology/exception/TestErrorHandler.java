package com.molcon.escalex.microbiology.exception;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class TestErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Scanner scanner = new Scanner(response.getBody());
        scanner.useDelimiter("\\Z");
        String data = "";
        if (scanner.hasNext())
            data = scanner.next();
        scanner.close();
        throw new InvalidToken(data);
    }
}
