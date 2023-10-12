package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Transaction;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.repository.TransactionRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public List<Transaction> findAllTransactionOfUser(User user){
        return transactionRepository.findByUser(user);
    }

}
