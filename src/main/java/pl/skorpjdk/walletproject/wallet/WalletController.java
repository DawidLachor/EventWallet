package pl.skorpjdk.walletproject.wallet;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.skorpjdk.walletproject.person.PersonDto;
import pl.skorpjdk.walletproject.person.PersonService;
import pl.skorpjdk.walletproject.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;

    @PostMapping
    public void createWallet(@RequestBody WalletDto walletDto){
        walletService.create(walletDto);
    }

    @GetMapping("/all")
    public List<WalletDto> allWallet(){
        return walletService.findAll();
    }

    @GetMapping("/{id}")
    public WalletDto allWallet(@PathVariable Long id){
        return walletService.findById(id);
    }
}