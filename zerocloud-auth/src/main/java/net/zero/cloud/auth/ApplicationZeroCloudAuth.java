package net.zero.cloud.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@EnableScheduling
@Component
@Slf4j
public class ApplicationZeroCloudAuth implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationZeroCloudAuth.class, args);
  }

  //@Autowired

  @Override
  public void run(ApplicationArguments args) {
    log.info("从这里开始");
  }
}
