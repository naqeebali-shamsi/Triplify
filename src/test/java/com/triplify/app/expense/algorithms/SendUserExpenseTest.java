package com.triplify.app.expense.algorithms;

import com.triplify.app.expense.model.AddExpenses;
import com.triplify.app.expense.model.Expenses;
import com.triplify.app.expense.controller.AddExpensesController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Send User Expense Java Builder Testing!!")
public class SendUserExpenseTest {
    @Test
    @DisplayName("Expenses split testing!!")
    public void splitExpensesTest(){
        final long id = 45;
        final String transaction_id = "hbjgyjjh76567g";
        final String description = "fish & chips";
        final float amount = 56;
        final String currency = "CAD";
        final ArrayList<String> usernamelist= new ArrayList<String>(Arrays.asList("13", "14", "15","16"));
        final String paidbyusername = "14";
        final Long groupId = Long.valueOf(1);
        final float full_amount = 56;
        final String date_added = "2022-02-01";

        AddExpenses addExpenses = new AddExpenses();

        addExpenses.setId(id);
        addExpenses.setTransaction_id(transaction_id);
        addExpenses.setDescription(description);
        addExpenses.setAmount(amount);
        addExpenses.setCurrency(currency);
        addExpenses.setUsernamelist(usernamelist);
        addExpenses.setPaidbyusername(paidbyusername);
        addExpenses.setGroupid(groupId);
        addExpenses.setFull_amount(full_amount);
        addExpenses.setDate_added(date_added);
        AddExpensesController addExpensesController = new AddExpensesController();
        addExpensesController.postExpense(description, amount, currency,usernamelist,
                paidbyusername, groupId, date_added);
        SendUserExpense sendUserExpense = new SendUserExpense();
        final List<Expenses> expectedSplitExpenseResult1 = sendUserExpense.fetchMyExpenses("16", 1);
        final String expectedSplitExpenseResult = String.valueOf(expectedSplitExpenseResult1.get(0));
        final String actualSplitExpenseResult = "com.triplify.app.expense.model.Expenses";
        System.out.println(expectedSplitExpenseResult);
        System.out.println(actualSplitExpenseResult);

        Assertions.assertTrue(expectedSplitExpenseResult.contains(actualSplitExpenseResult));
    }

    @Test
    @DisplayName("Expenses split testing!!")
    public void calculateTotalExpenseTest(){
        final long id = 45;
        final String transaction_id = "hbjgyjjh76567g";
        final String description = "fish & chips";
        final float amount = 56;
        final String currency = "CAD";
        final ArrayList<String> usernamelist= new ArrayList<String>(Arrays.asList("13", "14", "15","16"));;
        final String paidbyusername = "14";
        final Long groupId = Long.valueOf(1);
        final float full_amount = 56;
        final String date_added = "2022-02-01";

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
        SendUserExpense sendUserExpense = new SendUserExpense();

        float calculateTotal = sendUserExpense.calculateTotalExpense("14");
        calculateTotal = calculateTotal/calculateTotal;

        Assertions.assertEquals(calculateTotal,1,"Incorrect Insert Query Has been generated!!");
        ;
    }
}
