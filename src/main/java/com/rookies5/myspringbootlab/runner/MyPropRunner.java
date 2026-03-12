package com.rookies5.myspringbootlab.runner;

import com.rookies5.myspringbootlab.MyEnvironment;
import com.rookies5.myspringbootlab.properties.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    private final MyPropProperties myPropProperties;
    private final MyEnvironment myEnvironment;

    public MyPropRunner(MyPropProperties myPropProperties, MyEnvironment myEnvironment) {
        this.myPropProperties = myPropProperties;
        this.myEnvironment = myEnvironment;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.debug("=== @Value로 읽은 값 ===");
        logger.info("username = {}", username);
        logger.info("port = {}", port);

        logger.debug("=== MyPropProperties로 읽은 값 ===");
        logger.info("username = {}", myPropProperties.getUsername());
        logger.info("port = {}", myPropProperties.getPort());

        logger.debug("=== 현재 실행 환경 ===");
        logger.info("mode = {}", myEnvironment.getMode());
    }
}