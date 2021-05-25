package pl.skorpjdk.walletproject.cost;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{id_person}/costs")
@AllArgsConstructor
public class CostController {
    private final CostService costService;

    @GetMapping()
    public List<CostDto> findAllCostByPersonId(@PathVariable("id_person") Long id){
        return costService.findAllCostByWallet(id);
    }

    @PostMapping()
    public CostDto createCost(@PathVariable("id_person") Long id, @RequestBody CostDto costDto){
        return costService.createCost(id, costDto);
    }
}
