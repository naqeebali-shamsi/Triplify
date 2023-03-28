package com.triplify.app.expense.controller;

import com.triplify.app.expense.algorithms.SendUserExpense;
import com.triplify.app.expense.model.Expenses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/users")
public class SendUserExpenseDataController implements ISendUserExpenseDataController {
    @GetMapping("/userexpenses")
    @Override
    public List<Expenses> getAllExpenseDetails(@RequestParam String username, @RequestParam long groupid) {
        SendUserExpense sendUserExpense = new SendUserExpense();
        return sendUserExpense.fetchMyExpenses(username, groupid);
    }

    @GetMapping("/calculatetotal")
    @Override
    public float calculateUserTotalExpense(@RequestParam String username) {
        SendUserExpense sendUserExpense = new SendUserExpense();
        return sendUserExpense.calculateTotalExpense(username);
    }
}
