package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.web.authentication.ComplexPassword;
import de.saphijaga.spoozer.web.authentication.PasswordChangeMatches;
import de.saphijaga.spoozer.web.authentication.PasswordMatches;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xce35l5 on 29.11.2015.
 */
@PasswordChangeMatches(message = "Die Passwörter stimmen nicht überein")
public class ChangePasswordRequest {
    @NotNull
    @NotEmpty
    @ComplexPassword(message = "Das Passwort muss Groß- und Keinbuchstaben, sowie Zahlen enthalten")
    private String password;
    @NotNull
    @NotEmpty
    private String password2;
    @NotNull
    @NotEmpty
    private String oldpassword;

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

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }
}
