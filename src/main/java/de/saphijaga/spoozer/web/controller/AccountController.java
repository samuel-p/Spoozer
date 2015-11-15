package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.response.GetUserAccountsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samuel on 14.11.15.
 */
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/getUserAccounts")
    @SendToUser("/setUserAccounts")
    public GetUserAccountsResponse getUserAccounts(UserDetails user) {
        List<AccountDetails> accountList = accountService.getAccountList(user);
        Map<String, AccountDetails> accounts = new HashMap<>();
        accountList.forEach(account -> accounts.put(account.getService().name().toLowerCase(), account));
        return new GetUserAccountsResponse(accounts);
    }

    public void sendGetUserAccountsResponse(UserDetails user) {
        GetUserAccountsResponse response = getUserAccounts(user);
        messagingTemplate.convertAndSendToUser(user.getUsername(), "/setUserAccounts", response);
    }
}