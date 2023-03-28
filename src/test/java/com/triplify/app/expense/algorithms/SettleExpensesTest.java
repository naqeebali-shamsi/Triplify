package com.triplify.app.expense.algorithms;

import com.triplify.app.expense.controller.AddExpensesController;
import com.triplify.app.expense.model.AddExpenses;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@DisplayName("Settle Expenses Testing!!")
public class SettleExpensesTest {
    @Test
    @DisplayName("Fetch Settle Expenses testing!!")
    public void fetchSettleExpensesTest(){
        final long id = 45;
        final String transaction_id = "hbjgyjjh76567g";
        final String description = "fish & chips";
        final float amount = 56;
        final String currency = "CAD";
        final ArrayList<String> usernamelist = new ArrayList<String>(Arrays.asList("13", "14", "15","16"));;
        final float full_amount = 56;
        final String date_added = "2022-02-01";

        final String paidbyusername = "14";
        final Long groupId = Long.valueOf(1);

        AddExpenses addExpenses = new AddExpenses();

        addExpenses.setId(id);
        addExpenses.setTransaction_id(transaction_id);
        addExpenses.setDescription(description);
        addExpenses.setAmount(amount);
        addExpenses.setCurrency(currency);
        addExpenses.setUsernamelist(usernamelist);
        addExpenses.setPaidbyusername(paidbyusername);
        addExpenses.setGroupid(groupId);
        AddExpensesController addExpensesController = new AddExpensesController();
        addExpensesController.postExpense(description, amount, currency,usernamelist,
        paidbyusername, groupId, date_added);

        final String userid = "15";
        final long groupid_demo = 1;

        SettleExpenses settleExpenses = new SettleExpenses();
        HashMap<String, Float> fetchExpenses = settleExpenses.fetchSettleExpenses(
                addExpenses.getPaidbyusername(), addExpenses.getGroupid());
        for(Map.Entry m:fetchExpenses.entrySet()){
            System.out.println(m.getKey()+" "+m.getValue());
        }
        Assertions.assertTrue(fetchExpenses.containsKey(userid));
        Assertions.assertNull(fetchExpenses.get(groupid_demo));
    }
}
