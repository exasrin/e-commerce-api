package com.enigma.challenge_tokonyadia_api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_transaction")
public class Transaction {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetail> transactionDetails;

    public Transaction(String id, Customer customer, LocalDateTime transactionDate, List<TransactionDetail> transactionDetails) {
        this.id = id;
        this.customer = customer;
        this.transactionDate = transactionDate;
        this.transactionDetails = transactionDetails;
    }

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
}
