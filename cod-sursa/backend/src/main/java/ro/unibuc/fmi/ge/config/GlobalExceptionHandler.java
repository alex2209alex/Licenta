package ro.unibuc.fmi.ge.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.unibuc.fmi.ge.exceptions.BadRequestException;
import ro.unibuc.fmi.ge.exceptions.ForbiddenException;
import ro.unibuc.fmi.ge.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException() {
        return ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ProblemDetail handleForbiddenException() {
        return ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException() {
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    }
}
