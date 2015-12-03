package de.saphijaga.spoozer.web.domain.request;

import de.saphijaga.spoozer.web.authentication.UpdateNameNotInUse;
import de.saphijaga.spoozer.web.authentication.UsernameNotInUse;
import de.saphijaga.spoozer.web.authentication.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xce35l5 on 14.11.2015.
 */
@UpdateNameNotInUse(message = "Dieser Benutzername wird bereits verwendet")
public class SaveUserRequest {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    private String name;

    @NotEmpty
    @NotNull
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
