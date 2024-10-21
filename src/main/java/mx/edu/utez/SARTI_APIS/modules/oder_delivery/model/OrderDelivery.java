package mx.edu.utez.SARTI_APIS.modules.oder_delivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.address.model.Address;
import mx.edu.utez.SARTI_APIS.modules.delivery_man.model.DeliveryMan;
import mx.edu.utez.SARTI_APIS.modules.order.model.Order;

import java.time.Instant;

@Entity
@Table(name = "orders_deliveries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true, updatable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false, updatable = false)
    private OrderDeliveryTypes orderDeliveryType;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double fee;

    @Column(columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private Boolean status;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id",
            referencedColumnName = "id",
            nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_man_id",
            referencedColumnName = "id")
    private DeliveryMan deliveryMan;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "address_id",
            referencedColumnName = "id",
            nullable = false)
    private Address address;

    // Relationships ->

    // Methods
    @PrePersist
    public void setInitialValues() {
        this.status = true;
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
