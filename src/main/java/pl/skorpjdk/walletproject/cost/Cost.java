package pl.skorpjdk.walletproject.cost;

import lombok.Data;
import pl.skorpjdk.walletproject.file.FileDB;
import pl.skorpjdk.walletproject.person.Person;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Instant dateOfPay;
    private double cost;
    private String photos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_cost",
            joinColumns = @JoinColumn(name = "cost_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private Person person;
    @OneToMany(mappedBy = "cost", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<FileDB> file;
}
