package pl.skorpjdk.walletproject.summary;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/{id_wallet}/summary")
@AllArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping
    public List<SummaryCost> summaryCosts(@PathVariable("id_wallet") long idWallet){
        return summaryService.summaryCosts(idWallet);
    }
}
