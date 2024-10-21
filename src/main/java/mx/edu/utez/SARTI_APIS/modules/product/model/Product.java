package mx.edu.utez.SARTI_APIS.modules.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.cart_product.model.CartProduct;
import mx.edu.utez.SARTI_APIS.modules.order_product.model.OrderProduct;
import mx.edu.utez.SARTI_APIS.modules.product_image.model.ProductImage;
import mx.edu.utez.SARTI_APIS.modules.rate.model.Rate;
import mx.edu.utez.SARTI_APIS.modules.seller.model.Seller;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mainImage;

    @Column(columnDefinition = "DECIMAL(10,2)", nullable = false)
    private Double price;

    @Column(columnDefinition = "SMALLINT(5) UNSIGNED", nullable = false)
    private Integer stock;

    @Column(columnDefinition = "DECIMAL(2,1) UNSIGNED", nullable = false)
    private Double rating;

    @Column(columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private Boolean status;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "seller_id",
            referencedColumnName = "id",
            nullable = false)
    private Seller seller;

    // Relationships ->
    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL)
    private Set<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<CartProduct> cartProducts;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "product",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"product"})
    private Set<Rate> rates;

    // Methods
    @PrePersist
    public void setInitialValues() {
        this.rating = 0.0;
        this.status = true;
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void setUpdatedValues() {
        this.updatedAt = Instant.now();
    }
}
