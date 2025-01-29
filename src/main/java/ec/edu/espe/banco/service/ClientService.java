package ec.edu.espe.banco.service;

import ec.edu.espe.banco.api.dto.ClientDTO;
import ec.edu.espe.banco.entity.ClientEntity;
import ec.edu.espe.banco.exception.DeleteException;
import ec.edu.espe.banco.exception.InsertException;
import ec.edu.espe.banco.exception.UpdateException;
import ec.edu.espe.banco.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private String msgError;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void create(ClientDTO clientDTO) throws InsertException {
        try {
            ClientEntity clientToCreate = new ClientEntity();
            clientToCreate.setName(clientDTO.getName());
            clientToCreate.setLastname(clientDTO.getLastname());
            clientToCreate.setEmail(clientDTO.getEmail());
            clientToCreate.setPhone(clientDTO.getPhone());
            clientToCreate.setAddress(clientDTO.getAddress());
            clientToCreate.setBirthDate(clientDTO.getBirthDate());

            this.clientRepository.save(clientToCreate);

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error creating new client" : this.msgError;
            throw new InsertException(this.msgError, ClientEntity.class.getName());
        }
    }

    public void update(ClientDTO clientDTO) throws UpdateException {
        try {
            ClientEntity clientToUpdate = this.clientRepository.findByEmail(clientDTO.getEmail());

            if (clientToUpdate == null) {
                this.msgError = "Client email doesn't exist";
                throw new UpdateException(this.msgError, ClientEntity.class.getName());
            }

            clientToUpdate.setName(clientDTO.getName() != null ? clientDTO.getName() : clientToUpdate.getName());
            clientToUpdate.setLastname(clientDTO.getLastname() != null ? clientDTO.getLastname() : clientToUpdate.getLastname());
            clientToUpdate.setPhone(clientDTO.getPhone() != null ? clientDTO.getPhone() : clientToUpdate.getPhone());
            clientToUpdate.setBirthDate(clientDTO.getBirthDate() != null ? clientDTO.getBirthDate() : clientToUpdate.getBirthDate());
            clientToUpdate.setAddress(clientDTO.getAddress() != null ? clientDTO.getAddress() : clientToUpdate.getAddress());

            this.clientRepository.save(clientToUpdate);

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error updating client" : this.msgError;
            throw new UpdateException(this.msgError, ClientEntity.class.getName());
        }
    }

    public void delete(Integer id) throws DeleteException {
        try {
            Optional<ClientEntity> clientEntityOptional = this.clientRepository.findById(id);

            if (!clientEntityOptional.isPresent()) {
                this.msgError = "Client doesn't exist";
                throw new DeleteException(this.msgError, ClientEntity.class.getName());
            }

            this.clientRepository.delete(clientEntityOptional.get());

        } catch (Exception exception) {
            this.msgError = this.msgError == null ? "Error deleting client" : this.msgError;
            throw new DeleteException(this.msgError, ClientEntity.class.getName());
        }
    }
}
