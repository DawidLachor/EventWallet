package pl.skorpjdk.walletproject.email;

public interface EmailSender {
    void send(String to, String bodyEmail);
}
