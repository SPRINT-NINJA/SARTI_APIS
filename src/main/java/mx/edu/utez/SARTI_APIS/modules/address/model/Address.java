package mx.edu.utez.SARTI_APIS.modules.address.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.customer.model.Customer;
import mx.edu.utez.SARTI_APIS.modules.oder_delivery.model.OrderDelivery;
import mx.edu.utez.SARTI_APIS.modules.seller.model.Seller;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String country;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String state;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String city;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String locality;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String colony;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String street;

    @Column(columnDefinition = "SMALLINT(5)", nullable = false)
    private Integer zipCode;

    @Column(columnDefinition = "VARCHAR(5)", nullable = false)
    private String externalNumber;

    @Column(columnDefinition = "VARCHAR(5)", nullable = false)
    private String internalNumber;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String referenceNear;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private AddressTypes addressType;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-

    // Relationships ->
    @OneToOne(mappedBy = "address",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "address",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Seller seller;

    @OneToMany(mappedBy = "address",
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
