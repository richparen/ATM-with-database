package com.atm.atm.controller;

import com.atm.atm.Center.Account;
import com.atm.atm.service.AccountService;
import com.atm.atm.service.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class ATMController {
    @Autowired
    AccountService accountService;
    @Autowired
    DBConnection DBConnection;




    private List<String> tasks = Arrays.asList("View Balance", "Withdraw", "Deposit", "Change PIN", "Exit");
    private String custName;
    private Account theAccount;


    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("tasks", tasks);
        model.addAttribute("account", new Account());
        return "welcome";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute Account formAccount, Model model, HttpSession session) {
        int result = accountService.checkLogin(formAccount);
        if (result == 1) {
            model.addAttribute("error", "Account number does not exist");
            System.out.println("Login error");
            return "welcome";
        } else if (result == 2) {
            model.addAttribute("error", "Incorrect pin");
            System.out.println("Login error");
            return "welcome";
        } else if (result == 3) {
            model.addAttribute("error", "Account locked");
            System.out.println("Login error");
            return "welcome";
        }
        else {
            System.out.println("Login successful");
            //set session if login is successful and return menu page
            session.setAttribute("accountNum", formAccount.getAccountNo());
            return "menu";
        }
    }


    @GetMapping("/balance")
    public String mainBalance(Model model,  HttpSession session) {

        float bal = accountService.getAccountByAccountNo((int)session.getAttribute("accountNum")).getBalance();
        model.addAttribute("balance", bal);
        return "balance";
    }

    @GetMapping("/withdraw")
    public String menu(Model model, HttpSession session) {
        model.addAttribute("account", new Account());
        int accnum = (int)session.getAttribute("accountNum");
        model.addAttribute("accnum", accnum);
        return "withdraw";
    }

    @GetMapping("/accounts")
    private String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "welcome";
    }

    @GetMapping("/menu")
    public String goToMenu() {
        return "menu";
    }

    @GetMapping("/logout")
    public String logout(Model model,HttpSession session) {
        session.invalidate();
        model.asMap().clear();
        //session.removeAttribute("account");
        model.addAttribute("account", new Account());
        return "welcome";
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@ModelAttribute Account account, HttpSession session, Model model) {
        int accountNum = (Integer)session.getAttribute("accountNum");
        boolean result = accountService.withdrawMoney(accountNum,
                account.getBalance());
        float balance = accountService.getBalance(accountNum);
        if (result) {
            model.addAttribute("message", "You withdrew $" + account.getBalance());
            model.addAttribute("balance", balance);
            model.addAttribute("accountNum", accountNum);
        } else {
            model.addAttribute("message", "Low balance. Could not withdraw. ");
        }
        return "withdraw";
    }
    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("account", new Account());
        return "deposit";
    }

    @PostMapping("/changepin")
    public String changepin(){
         return "update";
    }

    @PostMapping("/deposit")
    public String depositMoney(@ModelAttribute Account account, HttpSession session, Model model) {
        int accountNum = (Integer)session.getAttribute("accountNum");
        boolean result = accountService.depositMoney(accountNum,
                account.getBalance());
        float balance = accountService.getBalance(accountNum);
        if (result) {
            model.addAttribute("message", "You deposited $" + account.getBalance());
            model.addAttribute("balance", balance);
            model.addAttribute("accountNum", accountNum);
        } else {
            model.addAttribute("message", "Error while depositing.");
        }
        return "deposit";
    }



}
