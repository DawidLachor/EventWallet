package pl.skorpjdk.walletproject.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.skorpjdk.walletproject.person.Person;
import pl.skorpjdk.walletproject.person.PersonRepository;
import pl.skorpjdk.walletproject.person.PersonService;
import pl.skorpjdk.walletproject.user.User;
import pl.skorpjdk.walletproject.user.UserService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class MappingWallet {
    private final PersonRepository personRepository;
    private final UserService userService;

    public Wallet mappingWallet(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setName(walletDto.getName());
        wallet.setDescription(walletDto.getDescription());
        wallet.setEnable(true);
        List<User> users = new ArrayList<>();
        User user = userService.getCurrentUser();
        users.add(user);
        wallet.setUsers(users);

        Person person = new Person();
        person.setWallet(wallet);
        person.setName(user.getFirstName() + " " + user.getLastName());

        wallet.setPersons(List.of(person));
        return wallet;
    }

    public WalletDto toWalletDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.getId());
        walletDto.setName(wallet.getName());
        walletDto.setDescription(wallet.getDescription());
        List<String> people = getNamePerson(wallet.getPersons());
        walletDto.setPeople(people);
        return walletDto;
    }

    private List<String> getNamePerson(List<Person> people){
        List<String> namePeople = new ArrayList<>();
        for (Person p: people){
            Person person = personRepository.findById(p.getId()).orElseThrow(() -> new IllegalStateException(String.format("User not found by id: %s", p.getId())));
            namePeople.add(person.getName());
        }
        return namePeople;
    }
}
