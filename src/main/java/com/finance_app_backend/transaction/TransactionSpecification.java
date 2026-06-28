package com.finance_app_backend.transaction;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {

    public static Specification<Transaction> search(String query,
                                                    LocalDate startDate,
                                                    LocalDate endDate,
                                                    UUID userId) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("id"), userId));

            if (query != null && !query.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + query.toLowerCase() + "%"));
            }

            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), startDate));
            }

            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }
}
