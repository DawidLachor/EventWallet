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

@Service
@AllArgsConstructor
public class SummaryService {
    private final PersonService personService;
    private final CostService costService;

    public List<SummaryCost> summaryCosts(Long idWallet){

        List<CostDto> allCostByWallet = costService.findAllCostByWallet(idWallet);
        BigDecimal sumCost = getSumCost(allCostByWallet);

        List<Person> allPersonByWallet = personService.findAllByIdWallet(idWallet);

        return createSummaryList(sumCost, allPersonByWallet);
    }

    private BigDecimal getSumCost(List<CostDto> costList) {
        BigDecimal sum = new BigDecimal(0);
        for (CostDto costDto : costList) {
            sum = sum.add(BigDecimal.valueOf(costDto.getCost()));
        }
        return sum;
    }

    private List<SummaryCost> createSummaryList(BigDecimal sumCost, List<Person> allPersonByWallet) {
        List<SummaryCost> summaryCosts = new ArrayList<>();
        sumCost = sumCost.divide(BigDecimal.valueOf(allPersonByWallet.size()), 2, RoundingMode.HALF_UP);
        for(Person person: allPersonByWallet){
            List<CostDto> allCostByPerson = costService.findAllCostByPerson(person.getId());
            BigDecimal sumPerson = getSumCost(allCostByPerson);
            sumPerson = sumPerson.subtract(sumCost);
            summaryCosts.add(new SummaryCost(person.getName(), sumPerson.doubleValue()));
        }
        return summaryCosts;
    }
}
