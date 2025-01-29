package ec.edu.espe.banco.api;

import ec.edu.espe.banco.api.dto.TransferDTO;
import ec.edu.espe.banco.entity.TransferEntity;
import ec.edu.espe.banco.exception.DocumentNotFoundException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TransferDTO transferDTO) {
        try {
            this.transferService.create(transferDTO);
            return ResponseEntity.ok().body("Correct Creation");
        } catch (InsertException insertException) {
            return ResponseEntity.badRequest().body(insertException.getMessage());
        }
    }

    @GetMapping("/getSentTransfersByAccountsId")
    public ResponseEntity<ArrayList<TransferEntity>> getSentTransfersByAccountsId(@RequestParam("accountId") Integer id) {
        try {
            return ResponseEntity.ok(this.transferService.getSentTransfersByAccountId(id));
        } catch (DocumentNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getReceivedTransfersByAccountsId")
    public ResponseEntity<ArrayList<TransferEntity>> getReceivedTransfersByAccountsId(@RequestParam("accountId") Integer id) {
        try {
            return ResponseEntity.ok(this.transferService.getReceivedTransfersByAccountId(id));
        } catch (DocumentNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
