package ec.edu.espe.banco.service;

import ec.edu.espe.banco.api.dto.TransferDTO;
import ec.edu.espe.banco.entity.AccountEntity;
import ec.edu.espe.banco.entity.TransferEntity;
import ec.edu.espe.banco.exception.DocumentNotFoundException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.repository.AccountRepository;
import ec.edu.espe.banco.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class TransferService {

    private final TransferRepository transferRepository;

    private final AccountRepository accountRepository;

    private String msgError;

    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(rollbackFor = Exception.class) //esto sirve para que todo el metodo se ejecute por completo
    public void create(TransferDTO transferDTO) throws InsertException {
        try {
            //VALIDACION
            this.validateTransfer(transferDTO);

            AccountEntity sourceAccount = this.accountRepository.findById(transferDTO.getSourceAccountId()).get();
            AccountEntity targetAccount = this.accountRepository.findById(transferDTO.getTargetAccountId()).get();

            //REGISTRO TRANSFERENCIA
            TransferEntity transferToCreate = new TransferEntity();

            transferToCreate.setSourceAccount(sourceAccount);
            transferToCreate.setTargetAccount(targetAccount);
            transferToCreate.setAmount(transferDTO.getAmount());
            transferToCreate.setDate(new Date());
            this.transferRepository.save(transferToCreate);

            //DESCONTAR TRANSFERENCIA ORIGEN
            sourceAccount.setBalance(sourceAccount.getBalance() - transferDTO.getAmount());
            this.accountRepository.save(sourceAccount);

            //MANEJO DE ERRORES
            if(transferDTO.getAmount() == 30){
                throw new InsertException(this.msgError, TransferEntity.class.getName());
            }

            //ANADIR TRANSFERENCIA AL OBJETIVO
            targetAccount.setBalance(targetAccount.getBalance() + transferDTO.getAmount());
            this.accountRepository.save(targetAccount);

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error creating new transfer" : this.msgError;
            throw new InsertException(this.msgError, TransferEntity.class.getName());
        }
    }

      ///////////////////////////////
     /// METODOS DE VERIFICACION ///
    ///////////////////////////////

    private void validateTransfer(TransferDTO transferDTO) throws InsertException {
        Optional<AccountEntity> sourceAccountOptional = this.accountRepository.findById(transferDTO.getSourceAccountId()); //tipo de dato para preguntar si existe o no el objeto
        Optional<AccountEntity> targetAccountOptional = this.accountRepository.findById(transferDTO.getTargetAccountId());

        if (!sourceAccountOptional.isPresent()) { //valida existencia
            this.msgError = "Source account doen't exist";
            throw new InsertException(this.msgError, TransferEntity.class.getName());
        }

        if (!targetAccountOptional.isPresent()) { //valida existencia
            this.msgError = "Target account doen't exist";
            throw new InsertException(this.msgError, TransferEntity.class.getName());
        }

        if(sourceAccountOptional.get().equals(targetAccountOptional.get())) { //valida que no sean las mismas
            this.msgError = "Source and target account are the same";
            throw new InsertException(this.msgError, TransferEntity.class.getName());
        }

        if(sourceAccountOptional.get().getBalance() - transferDTO.getAmount() < 0) { //valida saldo suficiente
            this.msgError = "Source account balance less than the target account";
            throw new InsertException(this.msgError, TransferEntity.class.getName());
        }

        if (transferDTO.getAmount() <= 0) { //valida que no sean montos negativos
            this.msgError = "Invalid transfer amount";
            throw new InsertException(this.msgError, TransferEntity.class.getName());
        }

    }


    public ArrayList<TransferEntity> getSentTransfersByAccountId(Integer id) throws DocumentNotFoundException {
        try {
            ArrayList<TransferEntity> transfers = this.transferRepository.findBySourceAccountId(id);

            if (!transfers.isEmpty()) {
                return transfers;
            }

            this.msgError = "No transfers sent from this account";
            throw new DocumentNotFoundException(this.msgError, TransferEntity.class.getName());

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error getting account's sent transfers" : this.msgError;
            throw new DocumentNotFoundException(this.msgError, TransferEntity.class.getName());
        }
    }

    public ArrayList<TransferEntity> getReceivedTransfersByAccountId(Integer id) throws DocumentNotFoundException {
        try {
            ArrayList<TransferEntity> transfers = this.transferRepository.findByTargetAccountId(id);

            if (!transfers.isEmpty()) {
                return transfers;
            }

            this.msgError = "No transfers received on this account";
            throw new DocumentNotFoundException(this.msgError, TransferEntity.class.getName());

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error getting account's received transfers" : this.msgError;
            throw new DocumentNotFoundException(this.msgError, TransferEntity.class.getName());
        }
    }
}
