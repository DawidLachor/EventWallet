package pl.skorpjdk.walletproject.summary;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.skorpjdk.walletproject.cost.CostDto;

import java.util.List;

@RestController
@RequestMapping("/api/{id_wallet}/summary")
@AllArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    //zwracanie listy summaryCost po id portfela
    @GetMapping
    public List<SummaryCost> summaryCosts(@PathVariable("id_wallet") long idWallet){
        return summaryService.summaryCosts(idWallet);
    }

    //Zwrot kosztów
    @PostMapping("/{id_person}")
    public void repayment(@PathVariable("id_wallet") long idWallet, @PathVariable("id_person")long idPerson,@RequestBody CostDto costDto){
        summaryService.repayment(idWallet,idPerson,costDto);
    }
}
