package pl.skorpjdk.walletproject.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDB, String> {
}
