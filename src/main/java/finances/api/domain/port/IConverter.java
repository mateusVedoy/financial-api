package finances.api.domain.port;

import finances.api.domain.exception.BusinessValidationException;

public interface IConverter<T, E>{
    T convert(E obj) throws BusinessValidationException;
}
