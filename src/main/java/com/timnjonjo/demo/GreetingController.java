package com.timnjonjo.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping("/greeting")
    public Greeting greeting(HttpServletRequest request, @RequestParam(value="name", defaultValue="World")  String name) {
        logger.info("---------------Start of Trace-------------------");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            logger.info(String.format("%s,: %s", key, value));
        }
        String remoteAddress = request.getRemoteAddr();
        String xforwardedremoteAddress = request.getHeader("X-FORWARDED-FOR");

        logger.info(String.format("This is the Remote Address on Request Header: %s", remoteAddress));
        logger.info(String.format("This is the X-FORWARDED-FOR IP on Request Header: %s", xforwardedremoteAddress));
        logger.info("-----------------End of Trace-------------------");
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
