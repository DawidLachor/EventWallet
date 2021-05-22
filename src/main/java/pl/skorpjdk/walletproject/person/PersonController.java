package pl.skorpjdk.walletproject.person;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.skorpjdk.walletproject.cost.CostService;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final CostService costService;

    @GetMapping("{id}")
    public PersonDto findById(@PathVariable("id") Long id){
        return personService.findByIdReturnPersonDto(id);
    }

    @GetMapping("/{id}/person")
    public List<PersonDto> findAllListPersonById(@PathVariable("id") Long id){
        return personService.findAllById(id);
    }

    @PostMapping("/{id}/person")
    public PersonDto findAllListPersonById(@RequestBody PersonDto personDto, @PathVariable Long id){
        return personService.createNewPerson(id, personDto);
    }
}
