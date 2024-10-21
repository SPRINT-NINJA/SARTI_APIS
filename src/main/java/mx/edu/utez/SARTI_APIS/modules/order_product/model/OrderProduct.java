package mx.edu.utez.SARTI_APIS.modules.order_product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.order.model.Order;
import mx.edu.utez.SARTI_APIS.modules.product.model.Product;
import mx.edu.utez.SARTI_APIS.modules.rate.model.Rate;

@Entity
@Table(name = "order_products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderProduct {
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
    @JoinColumn(name = "order_id",
            referencedColumnName = "id",
            nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id",
            referencedColumnName = "id",
            nullable = false)
    private Product product;

    // Relationships ->
    @OneToOne(mappedBy = "orderProduct",
            cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"orderProduct"})
    private Rate rates;

    // Methods
}
