package mx.edu.utez.SARTI_APIS.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.customer.model.Customer;
import mx.edu.utez.SARTI_APIS.modules.delivery_man.model.DeliveryMan;
import mx.edu.utez.SARTI_APIS.modules.seller.model.Seller;
import mx.edu.utez.SARTI_APIS.modules.verification_code.model.VerificationCode;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(400)")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false, updatable = false)
    private Roles role;

    @Column(columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private Boolean blocked;

    @Column(columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private Boolean status;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private Instant lastAccess;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-

    // Relationships ->
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    @JsonIgnore
    private Set<VerificationCode> verificationCodes;

    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private Seller seller;

    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private DeliveryMan deliveryMan;

    // Methods
    @PrePersist
    public void setInitialValues() {
        this.status = true;
        this.blocked = false;
        this.createdAt = Instant.now();
        this.lastAccess = Instant.now();
    }

    @PreUpdate
    public void setUpdatedValues() {
        this.updatedAt = Instant.now();
    }
}
