package pl.skorpjdk.walletproject.cost;

import lombok.Data;
import pl.skorpjdk.walletproject.person.Person;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String description;
    private Instant dateOfPay;
    private double cost;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_cost",
            joinColumns = @JoinColumn(name = "cost_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private Person person;
}