package pl.skorpjdk.walletproject.file;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skorpjdk.walletproject.cost.Cost;

import java.util.List;

public interface FileRepository extends JpaRepository<FileDB, String> {
    List<FileDB> findAllByCost(Cost cost);
}
