package com.finance_app_backend.transaction;

import com.finance_app_backend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transaction_table")
@Data
public class Transaction {

    @Id@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;

    public Transaction() {
    }

    public Transaction(BigDecimal amount,
                       LocalDate date,
                       String description,
                       TransactionType type,
                       User user) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
        this.user = user;
    }
}
