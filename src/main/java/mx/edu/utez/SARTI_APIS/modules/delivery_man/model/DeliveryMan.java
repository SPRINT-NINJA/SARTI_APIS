package mx.edu.utez.SARTI_APIS.modules.delivery_man.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.oder_delivery.model.OrderDelivery;
import mx.edu.utez.SARTI_APIS.modules.user.model.User;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "delivery_men")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeliveryMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true, updatable = false)
    private String deliveryManNumber;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String fistLastName;

    @Column(columnDefinition = "VARCHAR(50)")
    private String secondLastName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String facePhoto;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String frontIdentificationPhoto;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String backIdentificationPhoto;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            nullable = false)
    private User user;

    // Relationships ->
    @OneToMany(mappedBy = "deliveryMan",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<OrderDelivery> orderDelivery;

    // Methods
    @PrePersist
    public void setInitialValues() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void setUpdatedValues() {
        this.updatedAt = Instant.now();
    }
}
