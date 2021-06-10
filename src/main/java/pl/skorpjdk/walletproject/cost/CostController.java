package pl.skorpjdk.walletproject.cost;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.skorpjdk.walletproject.person.PersonDto;

import java.util.List;

@RestController
@RequestMapping("/api/{id_wallet}/costs")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*",methods = {RequestMethod.OPTIONS,RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT},maxAge = 3600L,allowCredentials = "true"  )
public class CostController {
    private final CostService costService;

    @GetMapping()
    public List<CostDto> findAllCostByWalletId(@PathVariable("id_wallet") Long id){
        return costService.findAllCostByWallet(id);
    }

    @PostMapping("/{id_person}")
    public CostDto createCost(@PathVariable("id_person") Long id, @RequestBody CostDto costDto){
        return costService.createCost(id, costDto);
    }

    @PutMapping("/{id_person}")
    public void updateCost(@RequestBody CostDto costDto, @PathVariable("id_person") long idPerson){
        costService.updateCost(costDto, idPerson);
    }

    @GetMapping("/{id_cost}")
    public PersonDto findPersonByIdCost(@PathVariable("id_cost") Long costId){
        return costService.findPersonByCost(costId);
    }

    @DeleteMapping("/{id_cost}")
    public void delete(@PathVariable("id_cost") Long costId){
        costService.delete(costId);
    }
}
