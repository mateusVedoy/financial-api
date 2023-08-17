package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.service.CachingService;
import finances.api.application.service.SerializationService;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class FinancialOperationAbstract {

    @Autowired
    protected FinancialOperationRepository repository;
    @Autowired
    protected FinancialOperationConverter converter;
    @Autowired
    protected CachingService caching;
    @Autowired
    protected SerializationService<FinancialOperationDTO> serialization;

    protected boolean hasCache(String key) {
        return caching.hasCache(key);
    }
    protected byte[] getCache(String key) {
        return caching.getCache(key);
    }
    protected void setCache(String key, List<FinancialOperationDTO> list) {
        byte[] serialized = serialize(list);
        caching.setCache(key, serialized);
    }
    protected byte[] serialize(List<FinancialOperationDTO> list) {
        return serialization.serialize(list);
    }
    protected List<FinancialOperationDTO> deserialize(byte[] bytes) {
        return serialization.deserialize(bytes);
    }
    protected List<FinancialOperationDTO> convertList(List<FinancialOperation> list) {
        List<FinancialOperationDTO> operationList = new ArrayList<>();
        list.forEach(value -> {
            try {
                operationList.add(converter.convert(value));
            } catch (BusinessValidationError e) {
                throw new BusinessException("Error during conversion process", "convert.entity.to.dto");
            }
        });
        return operationList;
    }
}
