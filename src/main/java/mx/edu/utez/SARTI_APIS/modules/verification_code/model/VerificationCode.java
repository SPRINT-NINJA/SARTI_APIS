package mx.edu.utez.SARTI_APIS.modules.verification_code.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.SARTI_APIS.modules.user.model.User;

import java.time.Instant;

@Entity
@Table(name = "verification_codes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(5)", nullable = false, updatable = false)
    private String code;

    @Column(columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private Boolean used;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant expiresAt;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            nullable = false)
    private User user;

    // Relationships ->

    // Methods
    @PrePersist
    public void setInitialValues() {
        this.used = false;
        this.createdAt = Instant.now();
        // creation time + 5 minutes
        this.expiresAt = this.createdAt.plusSeconds(5 * 60L);
    }

    @PreUpdate
    public void setUpdatedValues() {
        this.updatedAt = Instant.now();
    }
}
