package pl.skorpjdk.walletproject.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        Pattern emailPattern = Pattern.compile(".*@.*\\..*");
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
