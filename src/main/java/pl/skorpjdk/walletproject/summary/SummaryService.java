package pl.skorpjdk.walletproject.summary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skorpjdk.walletproject.cost.CostDto;
import pl.skorpjdk.walletproject.cost.CostService;
import pl.skorpjdk.walletproject.person.Person;
import pl.skorpjdk.walletproject.person.PersonService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class SummaryService {
    private final PersonService personService;
    private final CostService costService;

    //Zwracanie listy summaryCost po id portfela
    public List<SummaryCost> summaryCosts(Long idWallet){

        List<SummaryCost> summaryCosts = new ArrayList<>();
        List<Person> allPersonByWallet = personService.findAllByIdWallet(idWallet);
        BigDecimal sum = getSumCost(allPersonByWallet);
        BigDecimal sumShareBySizeList = sum.divide(BigDecimal.valueOf(allPersonByWallet.size()), 2, RoundingMode.HALF_UP);

        for (Person person: allPersonByWallet){
            summaryCosts.add(new SummaryCost(person.getId(), person.getName(), BigDecimal.valueOf(person.getTotalCost()).subtract(sumShareBySizeList).doubleValue()));
        }

        return summaryCosts;
    }

    //Zwrot kosztów
    public void repayment(Long idWallet, Long idPerson, CostDto costDto){
        List<SummaryCost> summaryCostList = summaryCosts(idWallet);
        //wybaranie tylko tych kto jesst na plusie z kosztami
        List<SummaryCost> summaryCostSPositiveCost = summaryCostList.stream().filter(summaryCost -> summaryCost.getCost() > 0).collect(Collectors.toList());
        //podzielenie kwoty zwrotu na ilość osób na plus z kosztami
        BigDecimal costByPerson = BigDecimal.valueOf(costDto.getCost()).divide(BigDecimal.valueOf(summaryCostSPositiveCost.size()), 2, RoundingMode.HALF_UP);
        //Zwrot kosztów dla znalezionych osób po równo
        for (SummaryCost summaryCost: summaryCostSPositiveCost){
            Person person = personService.findPersonById(summaryCost.getId());
            person.setTotalCost(BigDecimal.valueOf(person.getTotalCost()).subtract(costByPerson).doubleValue());
            personService.save(person);
        }
        costService.createCost(idPerson, costDto);
    }


    private BigDecimal getSumCost(List<Person> personList) {
        BigDecimal sum = new BigDecimal(0);
        for (Person person : personList) {
            sum = sum.add(BigDecimal.valueOf(person.getTotalCost()));
        }
        return sum;
    }
}
