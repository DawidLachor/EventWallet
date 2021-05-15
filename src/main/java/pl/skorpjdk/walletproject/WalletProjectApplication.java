package pl.skorpjdk.walletproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WalletProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletProjectApplication.class, args);
    }

}
