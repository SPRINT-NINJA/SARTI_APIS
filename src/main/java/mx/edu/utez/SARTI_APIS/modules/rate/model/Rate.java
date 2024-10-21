package mx.edu.utez.SARTI_APIS.modules.rate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.order_product.model.OrderProduct;
import mx.edu.utez.SARTI_APIS.modules.product.model.Product;

import java.time.Instant;

@Entity
@Table(name = "rates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "SMALLINT(2) UNSIGNED", nullable = false)
    private Short rate;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String comment;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String image;

    @Column(columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private Boolean status;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "product_id",
            referencedColumnName = "id",
            nullable = false)
    @JsonIgnoreProperties({"rates"})
    private Product product;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id",
            referencedColumnName = "id",
            nullable = false)
    @JsonIgnore
    private OrderProduct orderProduct;

    // Relationships ->

    // Methods
    @PrePersist
    public void setInitialValues() {
        this.status = true;
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void setUpdatedValues() {
        this.updatedAt = Instant.now();
    }

}
