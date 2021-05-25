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
    private final CostService costService;

    @GetMapping("{id}")
    public PersonDto findById(@PathVariable("id") Long id){
        return personService.findByIdReturnPersonDto(id);
    }

    @GetMapping()
    public List<PersonDto> findAllListPersonById(@PathVariable("id_wallet") Long id){
        return personService.findAllByIdWalletMappingPersonDto(id);
    }

    @PostMapping()
    public PersonDto findAllListPersonById(@RequestBody PersonDto personDto, @PathVariable("id_wallet") Long id){
        return personService.createNewPerson(id, personDto);
    }
}
