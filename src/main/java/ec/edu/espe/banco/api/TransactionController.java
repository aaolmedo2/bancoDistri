package ec.edu.espe.banco.api;

import ec.edu.espe.banco.api.dto.TransactionDTO;
import ec.edu.espe.banco.entity.TransactionEntity;
import ec.edu.espe.banco.exception.DocumentNotFoundException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TransactionDTO transactionDTO) {
        try {
            this.transactionService.create(transactionDTO);
            return ResponseEntity.ok().body("Correct Creation");
        } catch (InsertException insertException) {
            return ResponseEntity.badRequest().body(insertException.getMessage());
        }
    }

    @GetMapping("/getTransactionsByAccountsId")
    public ResponseEntity<ArrayList<TransactionEntity>> getTransactionsByAccountsId(@RequestParam("accountId") Integer id) {
        try {
            return ResponseEntity.ok(this.transactionService.getTransactionsByAccountId(id));
        } catch (DocumentNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
