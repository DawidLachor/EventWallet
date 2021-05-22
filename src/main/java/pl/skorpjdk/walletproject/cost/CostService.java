package pl.skorpjdk.walletproject.cost;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skorpjdk.walletproject.person.Person;
import pl.skorpjdk.walletproject.person.PersonService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final PersonService personService;

    public List<CostDto> findAllCostByPerson(Long id) {
        Person person = personService.findPersonById(id);
        List<CostDto> costDtoList = new ArrayList<>();
        List<Cost> cost = person.getCost();
        for (Cost c: cost){
            Cost costRepo = costRepository.findById(c.getId()).orElseThrow(() -> new IllegalStateException(String.format("Cost by id %s don't find", c.getId())));
            costDtoList.add(mappingToCostDto(costRepo));
        }
        return costDtoList;
    }

    public CostDto createCost(Long id, CostDto costDto) {
        Person person = personService.findPersonById(id);
        Cost cost = mappingToCost(costDto);
        cost.setDateOfPay(Instant.now());
        cost.setPerson(person);
        costRepository.save(cost);
        return costDto;
    }

    private CostDto mappingToCostDto(Cost cost){
        CostDto costDto = new CostDto();
        costDto.setName(cost.getName());
        costDto.setId(cost.getId());
        costDto.setDescription(cost.getDescription());
        costDto.setCost(cost.getCost());
        costDto.setDateOfPay(cost.getDateOfPay());
        return costDto;
    }

    private Cost mappingToCost(CostDto costDto){
        Cost cost = new Cost();
        cost.setName(costDto.getName());
        cost.setDescription(costDto.getDescription());
        cost.setCost(costDto.getCost());
        return cost;
    }
}
