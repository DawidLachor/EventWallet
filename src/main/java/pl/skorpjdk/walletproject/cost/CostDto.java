package pl.skorpjdk.walletproject.cost;

import lombok.Data;

import java.time.Instant;

@Data
public class CostDto {
    private Long id;
    private String name;
    private String description;
    private Instant dateOfPay;
    private double cost;
}
