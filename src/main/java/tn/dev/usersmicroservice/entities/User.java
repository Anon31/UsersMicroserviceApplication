package tn.dev.usersmicroservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // ← permet de nommer chaque champ au moment de la création
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private LocalDate birthDate;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Column(nullable = false)
    private Boolean enabled = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),       // colonne PK de User
            inverseJoinColumns = @JoinColumn(name = "role_id") // colonne PK de Role
    )

    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="number",    column=@Column(name="billing_number")),
            @AttributeOverride(name="street",    column=@Column(name="billing_street")),
            @AttributeOverride(name="city",      column=@Column(name="billing_city")),
            @AttributeOverride(name="zipCode",   column=@Column(name="billing_zip_code")),
            @AttributeOverride(name="state",     column=@Column(name="billing_state"))
    })
    private Address billingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="number",    column=@Column(name="delivery_number")),
            @AttributeOverride(name="street",    column=@Column(name="delivery_street")),
            @AttributeOverride(name="city",      column=@Column(name="delivery_city")),
            @AttributeOverride(name="zipCode",   column=@Column(name="delivery_zip_code")),
            @AttributeOverride(name="state",     column=@Column(name="delivery_state"))
    })
    private Address deliveryAddress;
}

//firstname: string;
//lastname: string;
//username: string;
//password: string;
//email: string;
//phone: string;
//birthDate: Date;
//createdAt: Date;
//updatedAt: Date;
//isActive: boolean;
//roles: string[];
//billing_address?: {
//number: number;
//street: string;
//city: string;
//zipCode: number;
//state: string;
//    };
//delivery_address?: {
//number: number;
//street: string;
//city: string;
//zipCode: number;
//state: string
//    };