package ec.edu.espe.banco.api;

import ec.edu.espe.banco.api.dto.ClientDTO;
import ec.edu.espe.banco.exception.DeleteException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.exception.UpdateException;
import ec.edu.espe.banco.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ClientDTO clientDTO) {
        try {
            this.clientService.create(clientDTO);
            return ResponseEntity.ok().body("Correct Creation");
        } catch (InsertException insertException) {
            return ResponseEntity.badRequest().body(insertException.getMessage());
        }
    }

    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody ClientDTO clientDTO) {
        try {
            this.clientService.update(clientDTO);
            return ResponseEntity.ok().body("Correct update");
        } catch (UpdateException updateException) {
            return ResponseEntity.badRequest().body(updateException.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("clientId") Integer id) {
        try {
            this.clientService.delete(id);
            return ResponseEntity.ok().body("Correct delete");
        } catch (DeleteException deleteException) {
            return ResponseEntity.badRequest().body(deleteException.getMessage());
        }
    }

}
