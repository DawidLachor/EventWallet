package pl.skorpjdk.walletproject.summary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SummaryCost {
    private long id;
    private String name;
    private double cost;
}
