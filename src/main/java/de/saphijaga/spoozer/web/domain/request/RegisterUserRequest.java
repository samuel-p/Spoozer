package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.web.auth.ComplexPassword;
import de.saphijaga.spoozer.web.auth.PasswordMatches;
import de.saphijaga.spoozer.web.auth.ValidEmail;
import de.saphijaga.spoozer.web.auth.UsernameNotInUse;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by samuel on 15.10.15.
 */
@PasswordMatches
public class RegisterUserRequest {
    @NotNull
    @NotEmpty
    @UsernameNotInUse
    private String username;
    @NotNull
    @NotEmpty
    @ComplexPassword
    private String password;
    @NotNull
    @NotEmpty
    @ComplexPassword
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