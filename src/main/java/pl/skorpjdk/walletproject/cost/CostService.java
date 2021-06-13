package pl.skorpjdk.walletproject.cost;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skorpjdk.walletproject.person.Person;
import pl.skorpjdk.walletproject.person.PersonDto;
import pl.skorpjdk.walletproject.person.PersonService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final PersonService personService;

    //Wyszukiwanie wszystkich kosztów po id portfela
    public List<CostDto> findAllCostByWallet(Long idWallet){
        //Wyszukiwanie wszystkich osób z portfela o danym id
        List<Person> persons = personService.findAllByIdWallet(idWallet);
        List<CostDto> costList = new ArrayList<>();

        //Tworzenie listy z wszystkimi kosztami w portfelu
        for (Person person: persons){
            List<Cost> cost = person.getCost();
            costList.addAll(createListCostDto(cost));
        }
        return costList;
    }

    //Wyszukiwanie wszystkich kosztów od danej osoby
    public List<CostDto> findAllCostByPerson(Long id) {
        Person person = personService.findPersonById(id);
        List<CostDto> costDtoList;
        List<Cost> cost = person.getCost();
        costDtoList = createListCostDto(cost);
        return costDtoList;
    }

    //Tworzenie nowego kosztu
    public CostDto createCost(Long idPerson, CostDto costDto) {
        //Wyszukiwanie osoby o danym id
        Person person = personService.findPersonById(idPerson);
        //Dodawanie całkowitego kosztu do danej osoby
        person.setTotalCost(person.getTotalCost()+costDto.getCost());

        //Tworzenie kosztu
        Cost cost = mappingToCost(costDto);
        cost.setDateOfPay(Instant.now());
        cost.setPerson(person);
        //Zapis
        Cost save = costRepository.save(cost);
        costDto.setId(save.getId());
        return costDto;
    }

    //Modyfikacja kosztów
    public void updateCost(CostDto costDto, long idPerson) {
        //Wyszukiwanie osoby o danym id
        Person person = personService.findPersonById(idPerson);
        //Wyszukiwanie kosztu
        Cost cost = findCostByCostDto(costDto);
        //Modyfikacja całkowitej ceny kosztów danej osoby
        person.setTotalCost((person.getTotalCost() - cost.getCost())+costDto.getCost());
        //Modyfikacja kosztu
        cost.setDescription(costDto.getDescription());
        cost.setCost(costDto.getCost());
        cost.setName(costDto.getName());
        cost.setPerson(person);
        costRepository.save(cost);
    }

    public Cost findCostByCostDto(CostDto costDto){
        return costRepository.findById(costDto.getId()).orElseThrow(() -> new IllegalStateException(String.format("Cost by id %s don't find", costDto.getId())));
    }

    public PersonDto findPersonByCost(Long costId) {
        Cost cost = costRepository.findById(costId).orElseThrow(() -> new IllegalStateException(String.format("Not found cost by id: %s", costId)));
        Person person = cost.getPerson();
        return personService.findByIdReturnPersonDto(person.getId());
    }

    public void delete(Long costId) {
        Cost cost = costRepository.findById(costId).orElseThrow(() -> new IllegalStateException(String.format("Not found cost by id: %s", costId)));
        Person person = cost.getPerson();
        //Odjęcie kosztu z całkowitej kwoty
        person.setTotalCost(BigDecimal.valueOf(person.getTotalCost()).subtract(BigDecimal.valueOf(cost.getCost())).doubleValue());
        costRepository.delete(cost);
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
