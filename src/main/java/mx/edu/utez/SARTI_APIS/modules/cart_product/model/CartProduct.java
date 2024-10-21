package mx.edu.utez.SARTI_APIS.modules.cart_product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.cart.model.Cart;
import mx.edu.utez.SARTI_APIS.modules.product.model.Product;

@Entity
@Table(name = "cart_products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "SMALLINT(5) UNSIGNED", nullable = false)
    private Integer amount;

    @Column(columnDefinition = "DECIMAL(10,2)", nullable = false)
    private Double total;

    @Column(columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private Boolean status;

    // Relationships <-
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id",
            referencedColumnName = "id",
            nullable = false)
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id",
            referencedColumnName = "id")
    private Product product;

    // Relationships ->

    // Methods
}
