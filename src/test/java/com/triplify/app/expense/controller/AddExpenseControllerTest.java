package com.triplify.app.expense.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@DisplayName("Add expense post Builder Testing!!")
public class AddExpenseControllerTest {
    @Test
    @DisplayName("post expense testing!!")
    public void postExpenseTest() {
        final String description = "Fish & Chips";
        final float amount = 86;
        final String currency = "CAD";
        final ArrayList<String> usernamelist = new ArrayList<String>(Arrays.asList("abc", "bca", "asd","dsa"));
        final String paidbyusername = "abc";
        final long groupid = 5;
        final String date_added = "20/10/2022";

        AddExpensesController addExpensesController = new AddExpensesController();
        Map<String,Object> data =
                addExpensesController.postExpense(description, amount, currency, usernamelist, paidbyusername,
                        groupid, date_added);
        System.out.println(data.get("SUCCESS"));

        Assertions.assertTrue(data.get("SUCCESS").equals(true));

    }

    @Test
    @DisplayName("settle expense testing!!")
    public void settleExpenseTest() {
        final float amount = 32;
        final String fromusername = "karan";
        final String tousername = "shweta";
        final long groupid = 4;

        AddExpensesController addExpensesController = new AddExpensesController();
        Map<String,Object> data =
                addExpensesController.settleExpense(amount, fromusername, tousername, groupid);
        System.out.println(data.get("SUCCESS"));

        Assertions.assertTrue(data.get("SUCCESS").equals(true));

    }
}
