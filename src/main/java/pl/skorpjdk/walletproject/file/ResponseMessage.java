package pl.skorpjdk.walletproject.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {
    private String idFile;
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String idFile, String message) {
        this.idFile = idFile;
        this.message = message;
    }
}
