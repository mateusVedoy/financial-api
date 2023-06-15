package finances.api.infraestructure.repository;

import finances.api.application.converter.FinancialOperationModelConverter;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.domain.port.IRepository;
import finances.api.infraestructure.postgres.IFinancialOperationRepository;
import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FinancialOperationRepository implements IRepository<FinancialOperation> {

    @Autowired
    private IFinancialOperationRepository repository;
    @Autowired
    private FinancialOperationModelConverter converter;

    @Override
    public FinancialOperation save(FinancialOperation entity) {
        var model = convert(entity);
        repository.save(model);
        return entity;
    }

    @Override
    public List<FinancialOperation> findAll() {
      return convertList(repository.findAll());
    }

    private List<FinancialOperation> convertList(List<FinancialOperationModel> list) {
        List<FinancialOperation> operationList = new ArrayList<>();
        list.forEach(value -> {
            try {
                operationList.add(converter.convert(value));
            } catch (BusinessValidationError e) {
                throw new RuntimeException(e); //ajustar depois o tipo de erro
            }
        });
        return operationList;
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
