package finances.api.infraestructure.postgres;

import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface IFinancialOperationRepository extends JpaRepository<FinancialOperationModel, Long> {
    @Query(nativeQuery = true,
            value = "select * from financialoperation f where f.fop_executed_at between ':initial_date' and ':final_date'")
    public List<FinancialOperationModel> findCodeAndAmountFromFinancialOperationByPeriod(@Param("initial_date") String initialDate, @Param("final_date") String finalDate) ;
}