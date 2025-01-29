package ec.edu.espe.banco.repository;

import ec.edu.espe.banco.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TransferRepository extends JpaRepository<TransferEntity, Integer> {

    ArrayList<TransferEntity> findBySourceAccountId(Integer id);

    ArrayList<TransferEntity> findByTargetAccountId(Integer id);
}
