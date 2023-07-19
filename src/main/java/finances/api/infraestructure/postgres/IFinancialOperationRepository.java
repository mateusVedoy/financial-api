package finances.api.infraestructure.postgres;

import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface IFinancialOperationRepository extends JpaRepository<FinancialOperationModel, Long> {
    @Query(nativeQuery = true,
            value = "select * from financialoperation f where f.fop_executed_at between " + ":initialDate" + " and " + ":finalDate")
    public List<FinancialOperationModel> findFinancialOperationModelByPeriod(@Param("initialDate") LocalDate initialDate, @Param("finalDate") LocalDate finalDate) ;
}