package com.contacts.controller;

import com.contacts.dto.ContatoDTO;
import com.contacts.entity.Contato;
import com.contacts.service.ContatoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;


    @PostMapping
    public ResponseEntity<ContatoDTO> createContact(
            @Valid
            @RequestBody ContatoDTO contatoDTO){
        ContatoDTO contact = contatoService.saveContact(contatoDTO);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @GetMapping("/{idContato}" )
    public ResponseEntity<?> getContactById(
            @PathVariable("idContato") Long idContato){
        ContatoDTO contatoDTO = contatoService.findContactById(idContato);
        if(Objects.isNull(contatoDTO))
            return new ResponseEntity<>("Contato de identificador: "+idContato +" não cadastrado!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(contatoDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> getAllContacts(){
        List<ContatoDTO> listContacts = contatoService.findAllContact();
        return  ResponseEntity.ok(listContacts);
    }

    @DeleteMapping("/{idContato}" )
    public ResponseEntity<String> deleteContactById(
            @PathVariable("idContato") Long idContato){
        boolean isDeleted = contatoService.deleteContactById(idContato);
        if(isDeleted)
            return new ResponseEntity<>("Contado de identificador: "+ idContato + " deletado com sucesso!", HttpStatus.OK);

        return new ResponseEntity<>("Contato de identificador: "+idContato +" não cadastrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllContact(){
        boolean isDeleted = contatoService.deleteAllContact();
        if(isDeleted)
            return new ResponseEntity<>("Contatos deletados com sucesso!", HttpStatus.OK);

        return new ResponseEntity<>("Não existe contato a ser excluido!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<?> updateContactPut(
            @PathVariable("idContato") @NotNull Long idContato,
            @Valid @RequestBody ContatoDTO contatoDTO){
        ContatoDTO contatoAtualizado = contatoAtualizado = contatoService.updateContactPut(idContato, contatoDTO);
        if(Objects.nonNull(contatoAtualizado))
            return new ResponseEntity<>(contatoAtualizado, HttpStatus.OK);

        return new ResponseEntity<>("Contato de identificador: "+idContato +" não cadastrado!", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{idContato}")
    public ResponseEntity<?> updateContactPatch(
            @PathVariable("idContato") @NotNull Long idContato,
            @Valid @RequestBody ContatoDTO contatoDTO){
        ContatoDTO contatoAtualizado = contatoAtualizado = contatoService.updateContactPatch(idContato, contatoDTO);
        if(Objects.nonNull(contatoAtualizado))
            return new ResponseEntity<>(contatoAtualizado, HttpStatus.OK);

        return new ResponseEntity<>("Contato de identificador: "+idContato +" não cadastrado!", HttpStatus.NOT_FOUND);
    }
}
