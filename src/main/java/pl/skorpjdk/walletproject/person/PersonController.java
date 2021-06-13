package pl.skorpjdk.walletproject.person;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.skorpjdk.walletproject.cost.CostService;

import java.util.List;

@RestController
@RequestMapping("/api/{id_wallet}/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    //Wyszukiwaniu osoby op id
    @GetMapping("/{id}")
    public PersonDto findById(@PathVariable("id") Long id){
        return personService.findByIdReturnPersonDto(id);
    }

    //Wyszukiwanie wszystkich osób po id portfela
    @GetMapping()
    public List<PersonDto> findAllListPersonByIdWallet(@PathVariable("id_wallet") Long idWallet){
        return personService.findAllByIdWalletMappingPersonDto(idWallet);
    }

    @PostMapping()
    public PersonDto createPersonWithWallet(@RequestBody PersonDto personDto, @PathVariable("id_wallet") Long idWallet){
        return personService.createNewPerson(idWallet, personDto);
    }
}
