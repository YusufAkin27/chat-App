package customer.core.exception;





import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import result.Result;

@RestControllerAdvice
public class CustomerExceptionHandlerConfig extends Throwable {

    @ExceptionHandler(value = BusinessException.class)
    public Result businessExceptionHandler(BusinessException businessException) {
        return new Result(businessException.getMessage(), false);
    }

}