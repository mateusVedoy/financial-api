package finances.api.infraestructure.repository;

import finances.api.application.converter.FinancialOperationModelConverter;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.domain.port.IRepository;
import finances.api.infraestructure.postgres.IFinancialOperationRepository;
import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FinancialOperationRepository implements IRepository<FinancialOperation> {

    @Autowired
    private IFinancialOperationRepository repository;
    @Autowired
    private FinancialOperationModelConverter converter;

    @Override
    public FinancialOperation save(FinancialOperation entity) {
        var model = convertToModel(entity);
        repository.save(model);
        return entity;
    }

    @Override
    public List<FinancialOperation> findAll() {
      return convertList(repository.findAll());
    }

    @Override
    public FinancialOperation findById(Long id) {
        return convertToDomain(repository.findById(id).get());
    }

    @Override
    public List<FinancialOperation> findByPeriod(String initialDate, String finalDate) {
        List<FinancialOperationModel> results = repository.findCodeAndAmountFromFinancialOperationByPeriod(initialDate, finalDate);
        return convertList(results);
    }


    private FinancialOperation convertToDomain(FinancialOperationModel model) {
        try{
            return converter.convert(model);
        } catch (BusinessValidationError e) {
            throw new BusinessException("Error during converting model to entity", "convert.model.to.entity");
        }
    }

    private List<FinancialOperation> convertList(List<FinancialOperationModel> list) {
        List<FinancialOperation> operationList = new ArrayList<>();
        list.forEach(value -> {
            try {
                operationList.add(converter.convert(value));
            } catch (BusinessValidationError e) {
                throw new BusinessException("Error during converting model to entity", "convert.model.to.entity");
            }
        });
        return operationList;
    }

    private FinancialOperationModel convertToModel(FinancialOperation entity) {
        return new FinancialOperationModel(
                entity.getType(),
                entity.getAmount(),
                entity.getExecutedAt()
        );
    }
}
