package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.web.authentication.ComplexPassword;
import de.saphijaga.spoozer.web.authentication.PasswordMatches;
import de.saphijaga.spoozer.web.authentication.UsernameNotInUse;
import de.saphijaga.spoozer.web.authentication.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by samuel on 15.10.15.
 */
@PasswordMatches(message = "Die Passwörter stimmen nicht überein")
public class RegisterUserRequest {
    @NotNull
    @NotEmpty
    @UsernameNotInUse(message = "Dieser Benutzername wird bereits verwendet")
    private String username;
    @NotNull
    @NotEmpty
    @ComplexPassword(message = "Das Passwort muss Groß- und Keinbuchstaben, sowie Zahlen enthalten")
    private String password;
    @NotNull
    @NotEmpty
    private String password2;
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}