package com.enigma.challenge_tokonyadia_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_product")
public class Product {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", columnDefinition = "BIGINT CHECK (price >= 0)")
    private Long price;

    @Column(name = "stock", columnDefinition = "INT CHECK (stock > 0)")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "")
    @JoinColumn(name = "product_image_id", unique = true)
    private List<ProductImage> productImage;

}
