package pl.skorpjdk.walletproject.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skorpjdk.walletproject.person.Person;
import pl.skorpjdk.walletproject.person.PersonRepository;
import pl.skorpjdk.walletproject.person.PersonService;
import pl.skorpjdk.walletproject.user.User;
import pl.skorpjdk.walletproject.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final MappingWallet mappingWallet;

    public void create(WalletDto walletDto) {
        Wallet wallet = mappingWallet.mappingWallet(walletDto);
        walletRepository.save(wallet);
    }

    public List<WalletDto> findAll() {
        return walletRepository.findAll().stream()
                .map(mappingWallet::toWalletDto)
                .collect(Collectors.toList());
    }

    public WalletDto findById(Long id) {
        return mappingWallet.toWalletDto(findWalletById(id));
    }

    public Wallet findWalletById(Long id){
        return walletRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("Not found wallet by id: %s",id)));
    }
}
