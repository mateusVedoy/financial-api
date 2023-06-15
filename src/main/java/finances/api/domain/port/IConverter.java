package finances.api.domain.port;

import finances.api.domain.exception.BusinessValidationError;

public interface IConverter<T, E>{
    T convert(E obj) throws BusinessValidationError;
}
