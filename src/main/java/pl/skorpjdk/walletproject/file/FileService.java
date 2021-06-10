package pl.skorpjdk.walletproject.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.skorpjdk.walletproject.cost.Cost;
import pl.skorpjdk.walletproject.cost.CostDto;
import pl.skorpjdk.walletproject.cost.CostRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileDBRepository;
    private final CostRepository costRepository;

    public FileDB store(MultipartFile file, Long costId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Cost cost = costRepository.findById(costId).orElseThrow(() -> new IllegalStateException("No found cost"));
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), cost);

        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public Stream<FileDB> getAllFilesByCostId(Long costId) {
        Cost findCost = costRepository.findById(costId).orElseThrow(() -> new IllegalStateException("No find cost"));
        return fileDBRepository.findAllByCost(findCost).stream();
    }
}
