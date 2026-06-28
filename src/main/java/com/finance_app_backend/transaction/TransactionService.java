package com.finance_app_backend.transaction;

import com.finance_app_backend.exceptions.ResourceNotFoundException;
import com.finance_app_backend.lib.Mapper;
import com.finance_app_backend.requests.CreateTransactionRequest;
import com.finance_app_backend.responses.ApiResponseWrapper;
import com.finance_app_backend.responses.PagedResponse;
import com.finance_app_backend.user.User;
import com.finance_app_backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final Mapper mapper;

    /**
     * Retrieves all the {@link Transaction} objects which match the given query string. Also allows
     * transactions to be filtered by date range.
     *
     * @param query a string which will be searched for within the database
     * @param page an int which represents the result page number requested
     * @param size an int which represents the number of results to be shown on each page
     * @param startDate LocalDateTime showing the start date of filtering
     * @param endDate LocalDateTime showing the end date of filtering
     * @param userId the UUID of the user whose transactions are being searched
     * @return an {@link ApiResponseWrapper} containing a {@link PagedResponse} with the
     * found {@link TransactionDTO} objects within
     */
    public ApiResponseWrapper<PagedResponse<TransactionDTO>> searchTransactions(
            String query,
            int page,
            int size,
            LocalDate startDate,
            LocalDate endDate,
            UUID userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        Specification<Transaction> spec = TransactionSpecification.search(query, startDate, endDate, userId);

        Page<Transaction> resultPage = transactionRepository.findAll(spec, pageable);

        List<TransactionDTO> content = resultPage
                .getContent()
                .stream()
                .map(mapper::transactionToTransactionDTO)
                .toList();

        PagedResponse<TransactionDTO> pagedResponse = new PagedResponse<>(
                content,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages(),
                resultPage.isLast()
        );

        return new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Transactions searched successfully",
                pagedResponse
        );
    }

    /**
     * Creates a transaction object and adds it to the database
     *
     * @param request {@link CreateTransactionRequest} contains the amount, date,
     * description, and type
     * @return an {@link ApiResponseWrapper} containing a {@link TransactionDTO}
     */
    public ApiResponseWrapper<TransactionDTO> createTransaction(CreateTransactionRequest request, UUID userId) {

        User user = userService.getUserById(userId);

        Transaction transaction = transactionRepository.save(new Transaction(
                request.amount(),
                request.date(),
                request.description(),
                request.type(),
                user
        ));

        return new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Transaction created successfully",
                mapper.transactionToTransactionDTO(transaction)
        );
    }

    /**
     * Deletes the transaction with the given UUID.
     *
     * @param transactionId UUID of the transaction to be removed from the database
     * @return an {@link ApiResponseWrapper} containing a {@link TransactionDTO}
     */
    public ApiResponseWrapper<TransactionDTO> deleteTransaction(UUID transactionId, UUID userId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        transactionRepository.delete(transaction);

        return new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Transaction deleted successfully",
                mapper.transactionToTransactionDTO(transaction)
        );
    }

    /**
     * Creates a summary showing the total income, total
     * expenses and balance so far for the current month
     *
     * @param userId the {@link UUID} of the logged-in user
     * @return a {@link SummaryDTO} containing the total income
     * and total expenses.
     */
    public ApiResponseWrapper<SummaryDTO> getSummary(UUID userId) {
        User user = userService.getUserById(userId);

        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        BigDecimal totalIncome = transactionRepository.sumByUserAndType(user, TransactionType.INCOME, startDate, endDate);
        BigDecimal totalExpense = transactionRepository.sumByUserAndType(user, TransactionType.EXPENSE,  startDate, endDate);

        totalIncome = totalIncome != null ? totalIncome : BigDecimal.ZERO;
        totalExpense = totalExpense != null ? totalExpense : BigDecimal.ZERO;

        BigDecimal balance = totalIncome.subtract(totalExpense);

        SummaryDTO summary = new SummaryDTO(
                totalIncome,
                totalExpense,
                balance
        );

        return new ApiResponseWrapper<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Transaction summary retrieved successfully",
                summary
        );
    }
}