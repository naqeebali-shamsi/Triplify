package com.triplify.app.expense.controller;

import com.triplify.app.expense.algorithms.AddNewExpenses;
import com.triplify.app.expense.model.AddExpenses;
import com.triplify.app.expense.model.Expenses;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(path = "api/v1/users")
@CrossOrigin

public class AddExpensesController implements  IAddExpensesController{
    @PostMapping("/addexpenses")
    @Override
    public Map<String,Object> postExpense(@RequestParam("description") String description,
                            @RequestParam("amount") float amount,
                            @RequestParam("currency") String currency,
                            @RequestParam("usernamelist") ArrayList<String> usernamelist,
                            @RequestParam("paidbyusername") String paidbyusername,
                            @RequestParam("groupid") long groupid,
                            @RequestParam("dateadded") String date_added) {
        Map<String,Object> response = new HashMap<>();
        AddExpenses expenses = new AddExpenses();
        int upperbound = 25;
        Random rand = new Random();
        long int_random = rand.nextLong(upperbound);
        expenses.setId(int_random);
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        expenses.setTransaction_id(generatedString);
        expenses.setDescription(description);
        expenses.setAmount(amount);
        expenses.setCurrency(currency);
        expenses.setUsernamelist(usernamelist);
        expenses.setPaidbyusername(paidbyusername);
        expenses.setGroupid(groupid);
        expenses.setFull_amount(amount);
        expenses.setDate_added(date_added);
        AddNewExpenses addNewExpenses = new AddNewExpenses();
        response = addNewExpenses.splitExpenses(expenses);

        return response;
    }

    @PostMapping("/settleexpenses")
    @Override
    public Map<String, Object> settleExpense(@RequestParam("amount") float amount,
                              @RequestParam("fromusername") String fromusername,
                              @RequestParam("tousername") String tousername,
                              @RequestParam("groupid") long groupid) {

        Map<String, Object> response = new HashMap<>();
        Expenses expenses = new Expenses();
        int upperbound = 25;
        Random rand = new Random();
        long int_random = rand.nextLong(upperbound);
        expenses.setId(int_random);
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        expenses.setTransaction_id(generatedString);
        expenses.setDescription(" ");
        expenses.setAmount(amount);
        expenses.setCurrency("CAD");
        expenses.setFromUsername(fromusername);
        expenses.setToUsername(tousername);
        expenses.setGroupid(groupid);
        expenses.setFull_amount(amount);
        expenses.setDate_added(null);
        AddNewExpenses addNewExpenses = new AddNewExpenses();
        response = addNewExpenses.settleMyExpenses(expenses);

        return response;
    }
}