package finances.api.infraestructure.repository;

import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.port.IRepository;
import finances.api.infraestructure.postgres.IFinancialOperationRepository;
import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialOperationRepository implements IRepository<FinancialOperation> {

    @Autowired
    private IFinancialOperationRepository repository;
    @Override
    public FinancialOperation save(FinancialOperation entity) {
        var model = convert(entity);
        repository.save(model);
        return entity;
    }

    private FinancialOperationModel convert(FinancialOperation entity) {
        var model = new FinancialOperationModel(
                entity.getType(),
                entity.getAmount(),
                entity.getExecutedAt()
        );
        return model;
    }
}
