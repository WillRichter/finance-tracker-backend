package com.finance_app_backend.transaction;

import com.finance_app_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {

    @Query("SELECT SUM(t.amount) " +
            "FROM Transaction t " +
            "WHERE t.user = :user " +
            "AND t.type = :type " +
            "AND t.date >= :start " +
            "AND t.date <= :end"
    )
    BigDecimal sumByUserAndType(@Param("user") User user,
                                @Param("type") TransactionType type,
                                @Param("start") LocalDate start,
                                @Param("end") LocalDate end
    );

    Optional<Transaction> findByIdAndUserId(UUID id, UUID userId);
}