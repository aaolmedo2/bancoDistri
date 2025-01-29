package ec.edu.espe.banco.service;

import ec.edu.espe.banco.api.dto.TransactionDTO;
import ec.edu.espe.banco.entity.AccountEntity;
import ec.edu.espe.banco.entity.TransactionEntity;
import ec.edu.espe.banco.exception.DocumentNotFoundException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.repository.AccountRepository;
import ec.edu.espe.banco.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    private String msgError;

    @Transactional(rollbackFor = Exception.class) //esto sirve para que todo el metodo se ejecute por completo
    public void create(TransactionDTO transactionDTO) throws InsertException {
        try {
            this.validateTransaction(transactionDTO);

            AccountEntity account = this.accountRepository.findById(transactionDTO.getAccountId()).get();

            TransactionEntity transactionToCreate = new TransactionEntity();

            transactionToCreate.setAccount(account);
            transactionToCreate.setType(transactionDTO.getType());
            transactionToCreate.setAmount(transactionDTO.getAmount());
            transactionToCreate.setDescription(transactionDTO.getDescription());
            
            transactionToCreate.setDate(new Date());
            this.transactionRepository.save(transactionToCreate);

            //anadir a la cuenta
            //ANADIR TRANSACCION A LA CUENTA

            //AUMENTAR EN LA TABLA CUENTA
            account.setBalance(account.getBalance() + transactionDTO.getAmount());
            this.accountRepository.save(account);

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error creating new transaction" : this.msgError;
            throw new InsertException(this.msgError, TransactionEntity.class.getName());
        }
    }

    ///////////////////////////////
    /// METODOS DE VERIFICACION ///
    ///////////////////////////////

    private void validateTransaction(TransactionDTO transactionDTO) throws InsertException{
        Optional<AccountEntity> accountOptional = this.accountRepository.findById(transactionDTO.getAccountId());

        if (!accountOptional.isPresent()) {
            this.msgError = "Account doen't exist";
            throw new InsertException(this.msgError, TransactionEntity.class.getName());
        }

        if (transactionDTO.getAmount() <= 0) {
            this.msgError = "Invalid transfer amount";
            throw new InsertException(this.msgError, TransactionEntity.class.getName());
        }
    }


    public ArrayList<TransactionEntity> getTransactionsByAccountId(Integer id) throws DocumentNotFoundException {
        try {
            ArrayList<TransactionEntity> transactions = this.transactionRepository.findByAccountId(id);

            if (!transactions.isEmpty()) {
                return transactions;
            }
            this.msgError = "No transactions on this account";
            throw new DocumentNotFoundException(this.msgError, TransactionEntity.class.getName());

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error getting account's transactions" : this.msgError;
            throw new DocumentNotFoundException(this.msgError, TransactionEntity.class.getName());
        }
    }
}
