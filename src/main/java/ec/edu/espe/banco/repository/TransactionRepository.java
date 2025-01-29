package ec.edu.espe.banco.repository;

import ec.edu.espe.banco.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    ArrayList<TransactionEntity> findByAccountId(Integer id);
}
