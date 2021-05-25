package pl.skorpjdk.walletproject.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skorpjdk.walletproject.user.User;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findAllByUsers(User user);
}
