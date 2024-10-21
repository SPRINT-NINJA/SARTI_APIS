package mx.edu.utez.SARTI_APIS.modules.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.cart_product.model.CartProduct;
import mx.edu.utez.SARTI_APIS.modules.customer.model.Customer;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DECIMAL(10,2)", nullable = false)
    private Double total;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",
            referencedColumnName = "id",
            nullable = false)
    @JsonIgnore
    private Customer customer;

    // Relationships ->
    @OneToMany(mappedBy = "cart",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts;

    // Methods
    @PrePersist
    public void setInitialValues() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
