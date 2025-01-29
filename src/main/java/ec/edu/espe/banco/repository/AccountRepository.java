package ec.edu.espe.banco.repository;

import ec.edu.espe.banco.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    ArrayList<AccountEntity> findByClientId(Integer id);
}
