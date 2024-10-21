package mx.edu.utez.SARTI_APIS.modules.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.address.model.Address;
import mx.edu.utez.SARTI_APIS.modules.cart.model.Cart;
import mx.edu.utez.SARTI_APIS.modules.user.model.User;

import java.time.Instant;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true, updatable = false)
    private String customerNumber;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String fistLastName;

    @Column(columnDefinition = "VARCHAR(50)")
    private String secondLastName;

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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "address_id",
            referencedColumnName = "id",
            nullable = false)
    private Address address;

    // Relationships ->
    @OneToOne(mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private Cart cart;

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
