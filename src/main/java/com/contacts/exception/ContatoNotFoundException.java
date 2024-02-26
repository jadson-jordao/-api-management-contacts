package com.contacts.exception;

public class ContatoNotFoundException extends RuntimeException{
    public ContatoNotFoundException(){
        super("Contato não cadastrado");
    }
    public ContatoNotFoundException(String message){
        super(message);
    }
}
