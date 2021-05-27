package pl.skorpjdk.walletproject.cost;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skorpjdk.walletproject.person.Person;
import pl.skorpjdk.walletproject.person.PersonService;
import pl.skorpjdk.walletproject.summary.SummaryCost;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final PersonService personService;

    public List<CostDto> findAllCostByWallet(Long idWallet){
        List<Person> persons = personService.findAllByIdWallet(idWallet);
        List<CostDto> costList = new ArrayList<>();
        for (Person person: persons){
            List<Cost> cost = person.getCost();
            costList.addAll(createListCostDto(cost));
        }
        return costList;
    }

    public List<CostDto> findAllCostByPerson(Long id) {
        Person person = personService.findPersonById(id);
        List<CostDto> costDtoList;
        List<Cost> cost = person.getCost();
        costDtoList = createListCostDto(cost);
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

    public void updateCost( CostDto costDto, long idPerson) {
        Person person = personService.findPersonById(idPerson);
        Cost cost = findCostById(costDto);
        cost.setDescription(costDto.getDescription());
        cost.setCost(costDto.getCost());
        cost.setName(costDto.getName());
        cost.setPerson(person);
        costRepository.save(cost);
    }

    public  Cost findCostById(CostDto costDto){
        return costRepository.findById(costDto.getId()).orElseThrow(() -> new IllegalStateException(String.format("Cost by id %s don't find", costDto.getId())));
    }

    private List<CostDto> createListCostDto(List<Cost> cost) {
        List<CostDto> costDtoList = new ArrayList<>();
        for (Cost c: cost){
            Cost costRepo = costRepository.findById(c.getId()).orElseThrow(() -> new IllegalStateException(String.format("Cost by id %s don't find", c.getId())));
            costDtoList.add(mappingToCostDto(costRepo));
        }
        return costDtoList;
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
