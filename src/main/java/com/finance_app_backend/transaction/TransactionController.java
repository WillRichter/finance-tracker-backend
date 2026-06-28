package com.finance_app_backend.transaction;

import com.finance_app_backend.requests.CreateTransactionRequest;
import com.finance_app_backend.responses.ApiResponseWrapper;
import com.finance_app_backend.responses.PagedResponse;
import com.finance_app_backend.user.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<PagedResponse<TransactionDTO>>> searchTransactions(
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "startDate", required = false)LocalDate startDate,
            @RequestParam(name = "endDate", required = false)LocalDate endDate,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(
                transactionService.searchTransactions(query, page, size, startDate, endDate, user.getId()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<TransactionDTO>> createTransaction(
            @RequestBody @Valid CreateTransactionRequest request,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(transactionService.createTransaction(request, user.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponseWrapper<TransactionDTO>> deleteTransaction(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(transactionService.deleteTransaction(id, user.getId()), HttpStatus.OK);
    }

    @GetMapping(path = "/summary")
    public ResponseEntity<ApiResponseWrapper<SummaryDTO>> getTransactionSummary(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(transactionService.getSummary(user.getId()), HttpStatus.OK);
    }
}