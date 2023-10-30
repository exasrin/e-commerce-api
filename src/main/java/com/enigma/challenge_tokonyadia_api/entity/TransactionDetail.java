package com.enigma.challenge_tokonyadia_api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_transaction_detail")
public class TransactionDetail {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Long price;

    public TransactionDetail(String id, Transaction transaction, Product product, Integer quantity, Long price) {
        this.id = id;
        this.transaction = transaction;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public TransactionDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
