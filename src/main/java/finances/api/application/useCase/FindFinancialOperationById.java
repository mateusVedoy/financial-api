package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.application.service.CachingService;
import finances.api.application.service.SerializationService;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class FindFinancialOperationById extends FinancialOperationAbstract{

    private static final String CACHE_KEY= "FIND_BY_ID_";
    @Autowired
    private FinancialOperationRepository repository;
    @Autowired
    private FinancialOperationConverter converter;
    @Autowired
    private CachingService caching;
    @Autowired
    private SerializationService<FinancialOperationDTO> serialization;

    public APIResponse findById(Long id) {
        String cacheById = setCacheKey(id);
        try{
            if(hasCache(cacheById)) {
                byte[] bytes = getCache(cacheById);
                List<FinancialOperationDTO> dto = deserialize(bytes);
                return new ResponseSuccess<>(200, StatusMessage.SUCCESS.getValue(), dto);
            }
             FinancialOperationDTO dto = converter.convert(
                    repository.findById(id)
            );
            setCache(cacheById, List.of(dto));
             return new ResponseSuccess<>(200, StatusMessage.SUCCESS.getValue(), List.of(dto));
        }catch (NoSuchElementException ex) {
            return new ResponseSuccess<>(200, StatusMessage.EMPTY_SUCCESS.getValue(), List.of());
        }catch (Exception ex) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), ex);
        }
    }
    private String setCacheKey(Long id) {
        String idStr = String.valueOf(id);
        return CACHE_KEY.concat(idStr);
    }
}
