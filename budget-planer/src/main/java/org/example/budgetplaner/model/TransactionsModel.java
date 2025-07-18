package org.example.budgetplaner.model;
import java.time.LocalDate;


public class TransactionsModel {
        private long id;
        private LocalDate date;
        private double amount;
        private int category_id;
        private boolean is_income;

        public TransactionsModel(long id, LocalDate date, double amount, int category_id, boolean is_income) {
            this.id = id;
            this.date = date;
            this.amount = amount;
            this.category_id = category_id;
            this.is_income = is_income;
        }

        public long getId() { return id; }
        public LocalDate getDate() { return date; }
        public double getAmount() { return amount; }
        public int getCategoryId() { return category_id; }
        public boolean getType() { return is_income; }

        @Override
        public String toString() {
            return String.format("[%s] %.2f € - %s (%s)", date, amount, category_id, is_income);
        }
    }


