package ec.edu.espe.banco.api;

import ec.edu.espe.banco.api.dto.AccountDTO;
import ec.edu.espe.banco.entity.AccountEntity;
import ec.edu.espe.banco.exception.DocumentNotFoundException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody AccountDTO accountDTO) {
        try {
            this.accountService.create(accountDTO);
            return ResponseEntity.ok().body("Correct Creation");
        }catch (InsertException insertException) {
            return ResponseEntity.badRequest().body(insertException.getMessage());
        }
    }

    @GetMapping("/getAccountsById")
    public ResponseEntity<ArrayList<AccountEntity>> getAccountsById(@RequestParam("clientId") Integer id) {
        try {
            return ResponseEntity.ok(this.accountService.getAccountsByClientId(id));
        }catch (DocumentNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
