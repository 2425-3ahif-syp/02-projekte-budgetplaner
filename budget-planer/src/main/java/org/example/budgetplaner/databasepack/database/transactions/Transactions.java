package org.example.budgetplaner.databasepack.database.transactions;
import java.time.LocalDate;


public class Transactions {
        private long id;
        private LocalDate date;
        private double amount;
        private int categoryId;
        private String type; // "income" or "expense"

        public Transactions(long id, LocalDate date, double amount, int category_id, String type) {
            this.id = id;
            this.date = date;
            this.amount = amount;
            this.categoryId = category_id;
            this.type = type;
        }

        public long getId() { return id; }
        public LocalDate getDate() { return date; }
        public double getAmount() { return amount; }
        public int getCategoryId() { return categoryId; }
        public String getType() { return type; }

        @Override
        public String toString() {
            return String.format("[%s] %.2f € - %s (%s)", date, amount, categoryId, type);
        }
    }


