package com.contacts.handler;

import com.contacts.exception.ContatoNotFoundException;
import com.contacts.handler.message.ResponseMessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class ControlExceptionHandler {
    /**
     * Este metodo tem como função carregar a exception global gerada pelas validações da anotação @Valid utilizada no
     * controller, e realizar a tratativa da mensagem.
     * @param ex
     * @return ResponseEntity<ResponseMessageError>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessageError>  methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        ResponseMessageError response = new ResponseMessageError(BAD_REQUEST,"Consistência de dados de entrada");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msgError = error.getDefaultMessage();
            response.setMessage(field,msgError);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Este metodo tem como função tratar as exeception quando um contato não existir cadastra.
     * @param ex
     * @return ResponseEntity<ResponseMessageError>
     */
    @ExceptionHandler(ContatoNotFoundException.class)
    private ResponseEntity<ResponseMessageError> contatoNotFoundException(ContatoNotFoundException ex){
        log.error(ex.getMessage());
        ResponseMessageError response = new ResponseMessageError(HttpStatus.NOT_FOUND,ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
