package com.devexperts.rest;


import com.devexperts.model.Account;
import com.devexperts.model.Transfer;
import com.devexperts.repo.AccountRepository;
import com.devexperts.repo.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class WorkAccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @GetMapping("/account")
    public String account ( Model model) {
        Iterable <Account> accounts=accountRepository.findAll();
        model.addAttribute("accounts",accounts);
        return "account";
    }

    @GetMapping("/account/add")
    public String accoontAdd ( Model model) {
        return "account-add";
    }

    @PostMapping("/account/add")
    public String current_account_add (@RequestParam String first_name, @RequestParam String second_name, @RequestParam Double balance
                                    , Model model) {

        Account account=new Account(first_name,second_name,balance);
        accountRepository.save(account);
        return "redirect:/account";
    }
    @GetMapping("/account/{id}")
    public String accountDetails (@PathVariable(value = "id") long id, Model model) {
        Optional<Account> account= accountRepository.findById(id);
        ArrayList<Account> res=new ArrayList<>();
        account.ifPresent(res::add);
        model.addAttribute("account",res);
        return "account-details";
    }
    @GetMapping("/account/{id}/edit")
    public String accountEdit (@PathVariable(value = "id") long id, Model model) {
        Optional<Account> account= accountRepository.findById(id);
        ArrayList<Account> res=new ArrayList<>();
        account.ifPresent(res::add);
        model.addAttribute("account",res);
        return "account-edit";
    }

    @PostMapping("/account/{id}/edit")
    public String currentAccountEdit (@PathVariable(value = "id") long id, @RequestParam String first_name, @RequestParam String second_name,
                                   @RequestParam Double balance,
                                   Model model) {

        Account account=accountRepository.findById(id).orElseThrow();
        account.setFirst_name(first_name);
        account.setSecond_name(second_name);
        account.setBalance(balance);

        accountRepository.save(account);
        return "redirect:/account";
    }
    @PostMapping("/account/{id}/delete")
    public String currentaccountDelete (
            @PathVariable(value = "id") long id,
            Model model) {

        Account account=accountRepository.findById(id).orElseThrow();
        accountRepository.delete(account);
        return "redirect:/account";
    }
    @GetMapping("/account/{id}/transfer")
    public String transfer (@PathVariable(value = "id") long id, Model model) {
        Iterable <Account> accounts=accountRepository.findAll();
        model.addAttribute("accounts",accounts);
        return "transfer";
    }

    @PostMapping("/transfer/add")
    public String current_transfer_add (@RequestParam  Long source_id, @RequestParam Long target_id, @RequestParam Double amount
            , Model model) {

        Transfer transfer = new Transfer(source_id, target_id, amount, new Timestamp(System.currentTimeMillis()));
        transferRepository.save(transfer);
        Account account1 = accountRepository.findById(source_id).orElseThrow();
        Account account2 = accountRepository.findById(target_id).orElseThrow();
        account1.setBalance(account1.getBalance() - amount);
        account2.setBalance(account2.getBalance() + amount);
        accountRepository.save(account1);
        accountRepository.save(account2);


        return "redirect:/account";
    }

}
