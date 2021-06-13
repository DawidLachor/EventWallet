package pl.skorpjdk.walletproject.person;

import lombok.*;
import pl.skorpjdk.walletproject.cost.Cost;
import pl.skorpjdk.walletproject.wallet.Wallet;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Wallet wallet;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Cost> cost;
    private double totalCost;
}
