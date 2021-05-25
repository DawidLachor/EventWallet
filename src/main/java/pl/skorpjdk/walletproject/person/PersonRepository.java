package pl.skorpjdk.walletproject.person;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skorpjdk.walletproject.wallet.Wallet;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByWalletId(Long idWallet);
}
