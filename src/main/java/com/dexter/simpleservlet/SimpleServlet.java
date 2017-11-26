package com.dexter.simpleservlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

public class SimpleServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        readParam(req);
        resp.getWriter().println("Hello World");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        readBody(req);
        readParam(req);
        resp.getWriter().println("Hello World");
    }

    private void readBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(req.getInputStream());
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
        }
        log.info("Body : {}", sb);
    }

    private void readParam(HttpServletRequest req) {

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            log.info("Header name : {}, value : {}", headerName, req.getHeader(headerName));
        }

        req.getParameterMap().forEach((k ,v) -> {
            log.info("Key : {}", k);
            for(String value : v){
                log.info("Value : {}", value);
            }
        });

    }

    @Override
    public void init() throws ServletException {
        log.info("Servlet was initialized");
    }

    @Override
    public void destroy() {
        log.info("Servlet was destroyed");
    }

}
