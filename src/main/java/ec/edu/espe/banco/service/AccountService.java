package ec.edu.espe.banco.service;

import ec.edu.espe.banco.api.dto.AccountDTO;
import ec.edu.espe.banco.entity.AccountEntity;
import ec.edu.espe.banco.entity.ClientEntity;
import ec.edu.espe.banco.exception.DocumentNotFoundException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.repository.AccountRepository;
import ec.edu.espe.banco.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    private String msgError;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public void create(AccountDTO accountDTO) throws InsertException {
        try {
            Optional<ClientEntity> clientEntityOptional = this.clientRepository.findById(accountDTO.getClientId());

            if (!clientEntityOptional.isPresent()) {
                this.msgError = "Account doesn't exist";
                throw new InsertException(this.msgError, AccountEntity.class.getName());
            }

            AccountEntity accountToCreate = new AccountEntity();
            accountToCreate.setClient(clientEntityOptional.get());
            accountToCreate.setType(accountDTO.getType());
            accountToCreate.setNumber(accountDTO.getNumber());
            accountToCreate.setCreationDate(new Date());
            accountToCreate.setBalance(0);

            this.accountRepository.save(accountToCreate);
        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error creating new account" : this.msgError;
            throw new InsertException(this.msgError, AccountEntity.class.getName());
        }
    }

    public ArrayList<AccountEntity> getAccountsByClientId(Integer clientId) throws DocumentNotFoundException {
        try {
            ArrayList<AccountEntity> accounts = this.accountRepository.findByClientId(clientId);

            if (!accounts.isEmpty()) {
                return accounts;
            } else {
                this.msgError = "No accounts owned by client";
                throw new DocumentNotFoundException(this.msgError, AccountEntity.class.getName());
            }
        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error getting client's accounts" : this.msgError;
            throw new DocumentNotFoundException(this.msgError, AccountEntity.class.getName());
        }
    }
}
