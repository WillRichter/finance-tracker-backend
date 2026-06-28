package com.finance_app_backend.lib;

import com.finance_app_backend.transaction.Transaction;
import com.finance_app_backend.transaction.TransactionDTO;
import com.finance_app_backend.user.User;
import com.finance_app_backend.user.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }

    public TransactionDTO transactionToTransactionDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription(),
                transaction.getType()
        );
    }

}
