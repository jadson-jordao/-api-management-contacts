package com.contacts.handler.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class ResponseMessageError {
    public ResponseMessageError(HttpStatus status, String globalMessage) {
        this.status = status;
        this.globalMessage = globalMessage;
    }

    private HttpStatus status;
    private String globalMessage;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime datetime;

    private List<MessageError> messages;

    {
        messages = new ArrayList<>();
        datetime = LocalDateTime.now();
    }

    public void setMessage(String field, String message) {
        this.setMessages(Arrays.asList(new MessageError(field, message)));
    }
}
