package pl.skorpjdk.walletproject.person;

import lombok.Data;
import pl.skorpjdk.walletproject.cost.Cost;
import pl.skorpjdk.walletproject.wallet.Wallet;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Wallet wallet;
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Cost> cost;
}
