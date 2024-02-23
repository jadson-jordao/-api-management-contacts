package com.contacts.controller;

import com.contacts.dto.ContatoDTO;
import com.contacts.entity.Contato;
import com.contacts.entity.Endereco;
import com.contacts.repository.ContatoRepository;
import com.contacts.service.ContatoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<ContatoDTO> createContact(
            @RequestBody
            @Valid ContatoDTO contatoDTO){
        ContatoDTO contact = contatoService.saveContato(contatoDTO);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @GetMapping("/{idContato}" )
    public ResponseEntity<ContatoDTO> getContactById(
            @PathVariable("idContato") Long idContato){
        Optional<Contato> contato = contatoService.findById(idContato);
        return contato.map(value -> ResponseEntity.ok(modelMapper.map(value, ContatoDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping( )
    public ResponseEntity<List<ContatoDTO>> getAllContacts(){
        List<ContatoDTO> listContatos = contatoService.findAll();
        return  ResponseEntity.ok(listContatos);
    }

    @DeleteMapping("/{idContato}" )
    public ResponseEntity<String> deleteContactById(
            @PathVariable("idContato") Long idContato){
        boolean isDeleted = contatoService.deleteContactById(idContato);
        if(isDeleted)
            return new ResponseEntity<>("Contado de identificador: "+ idContato + " deletado com sucesso!", HttpStatus.OK);
        return new ResponseEntity<>("Contato de identificador: "+idContato +" não cadastrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping( )
    public ResponseEntity<String> deleteAllContact(){
        contatoService.deleteAllContact();
        return new ResponseEntity<>("Contatos deletados com sucesso!", HttpStatus.OK);
    }

}
