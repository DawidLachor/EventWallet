package pl.skorpjdk.walletproject.wallet;

import lombok.*;
import pl.skorpjdk.walletproject.person.Person;
import pl.skorpjdk.walletproject.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Boolean enable;
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Person> persons;
    @ManyToMany
    @JoinTable(name = "users_wallet",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "wallet_id", referencedColumnName = "id"))
    private List<User> users;
}
