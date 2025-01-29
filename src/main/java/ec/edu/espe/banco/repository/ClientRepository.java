package ec.edu.espe.banco.repository;

import ec.edu.espe.banco.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

    ClientEntity findByEmail(String email);
}
