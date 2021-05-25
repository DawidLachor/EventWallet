package pl.skorpjdk.walletproject.person;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skorpjdk.walletproject.wallet.Wallet;
import pl.skorpjdk.walletproject.wallet.WalletService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final WalletService walletService;

    public Person findPersonById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("Not found the person about id: %s", id)));
    }

    public PersonDto findByIdReturnPersonDto(Long id) {
        Person person = findPersonById(id);
        return mappingToPersonDto(person);
    }

    public List<Person> findAllByIdWallet(Long idWallet) {
        Wallet wallet = walletService.findWalletById(idWallet);
        List<Person> personList = new ArrayList<>();
        for (Person p: wallet.getPersons()){
            Person person = personRepository.findById(p.getId()).orElseThrow(() -> new IllegalStateException(String.format("User not found by id: %d", p.getId())));
            personList.add(person);
        }
        return personList;
    }

    public List<PersonDto> findAllByIdWalletMappingPersonDto(Long idWallet){
        return findAllByIdWallet(idWallet).stream()
                .map(this::mappingToPersonDto)
                .collect(Collectors.toList());
    }

    public PersonDto createNewPerson(Long id, PersonDto personDto) {
        Wallet walletById = walletService.findWalletById(id);
        Person person = mappingToPerson(personDto, walletById);
        List<Person> persons = walletById.getPersons();
        persons.add(person);
        personRepository.save(person);
        return mappingToPersonDto(person);
    }

    private PersonDto mappingToPersonDto(Person person){
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        return personDto;
    }

    private Person mappingToPerson(PersonDto personDto, Wallet wallet){
        Person person = new Person();
        person.setName(personDto.getName());
        person.setWallet(wallet);
        return person;
    }
}
