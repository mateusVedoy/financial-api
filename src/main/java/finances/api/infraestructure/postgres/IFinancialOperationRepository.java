package finances.api.infraestructure.postgres;

import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IFinancialOperationRepository extends JpaRepository<FinancialOperationModel, Long> {
}
