package org.example.budgetplaner.testdaten;

import org.example.budgetplaner.databasepack.database.BudgetReposetory;

import java.io.IOException;

public class TestDatenBudget {
    public static void main(String[] args){
        BudgetReposetory budgetReposetory = new BudgetReposetory();
        budgetReposetory.createNewBudgetPlan(2000.00f, 6, 2025, 1);
        budgetReposetory.createNewBudgetPlan(100.00f, 6, 2025, 2);
        budgetReposetory.createNewBudgetPlan(100.00f, 6, 2025, 3);
        budgetReposetory.createNewBudgetPlan(100.00f, 6, 2025, 4);
        budgetReposetory.createNewBudgetPlan(100.00f, 6, 2025, 5);
        budgetReposetory.createNewBudgetPlan(100.00f, 6, 2025, 6);
        budgetReposetory.createNewBudgetPlan(100.00f, 6, 2025, 7);

    }
}
