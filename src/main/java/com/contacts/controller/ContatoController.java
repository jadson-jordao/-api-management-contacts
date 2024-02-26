package com.contacts.controller;

import com.contacts.dto.ContatoCreateDTO;
import com.contacts.dto.ContatoResponseDTO;
import com.contacts.dto.ContatoUpdateDTO;
import com.contacts.handler.message.ResponseMessageError;
import com.contacts.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/contatos")
@Tag(name =  "Operações de CRUD de um Contato")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @Operation(summary = "Serviço de criação de um novo contato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato Criado com Sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContatoResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar contato",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessageError.class)) })
    })
    @PostMapping
    public ResponseEntity<ContatoResponseDTO> createContact(
            @Valid
            @RequestBody ContatoCreateDTO contatoCreateDTO){
        ContatoResponseDTO contact = contatoService.saveContact(contatoCreateDTO);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @Operation(summary = "Serviço de consulta de um contato especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato pesquisado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContatoResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Contato não existe na base",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessageError.class)) })
    })
    @GetMapping("/{idContato}" )
    public ResponseEntity<?> getContactById(
            @PathVariable("idContato") Long idContato){
        ContatoResponseDTO contatoRequestDTO = contatoService.findContactById(idContato);
        if(Objects.isNull(contatoRequestDTO))
            return new ResponseEntity<>("Contato de identificador: "+idContato +" não cadastrado!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(contatoRequestDTO, HttpStatus.OK);
    }

    @Operation(summary = "Serviço de consulta de todos os contatos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos pesquisados com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContatoResponseDTO.class)) }),
    @ApiResponse(responseCode = "404", description = "Não existe contato cadastrado",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessageError.class)) })
    })
    @GetMapping
    public ResponseEntity<List<ContatoResponseDTO>> getAllContacts(){
        List<ContatoResponseDTO> listContacts = contatoService.findAllContact();
        return  ResponseEntity.ok(listContacts);
    }

    @Operation(summary = "Serviço de exclusão de um contato especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato excluido com sucesso",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Contato não cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessageError.class)) })
    })
    @DeleteMapping("/{idContato}" )
    public ResponseEntity<ResponseMessageError> deleteContactById(
            @PathVariable("idContato") Long idContato){
        contatoService.deleteContactById(idContato);

        return new ResponseEntity<>(new ResponseMessageError(HttpStatus.OK,
                "Contado de identificador: "+ idContato + " deletado com sucesso!"), HttpStatus.OK);
    }

    @Operation(summary = "Serviço de exclusão de todos os contatos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos excluidos com sucesso",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Contato não cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessageError.class)) })
    })
    @DeleteMapping
    public ResponseEntity<ResponseMessageError> deleteAllContact(){
        contatoService.deleteAllContact();
        return new ResponseEntity<>(new ResponseMessageError(HttpStatus.OK,
                "Contatos deletados com suucesso!"), HttpStatus.OK);
    }

    @Operation(summary = "Serviço de atualização completa de um contato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContatoResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Contato não cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessageError.class)) })
    })
    @PutMapping("/{idContato}")
    public ResponseEntity<?> updateContactPut(
            @PathVariable("idContato") @NotNull Long idContato,
            @Valid @RequestBody ContatoUpdateDTO contatoUpdateDTO){
        ContatoResponseDTO contatoAtualizado = contatoAtualizado = contatoService.updateContactPut(idContato, contatoUpdateDTO);
        return new ResponseEntity<>(contatoAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Serviço de atualização parcial de um contato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContatoResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Contato não cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessageError.class)) })
    })
    @PatchMapping("/{idContato}")
    public ResponseEntity<?> updateContactPatch(
            @PathVariable("idContato") @NotNull Long idContato,
            @Valid @RequestBody ContatoUpdateDTO contatoUpdateDTO){
        ContatoResponseDTO contatoAtualizado = contatoAtualizado = contatoService.updateContactPatch(idContato, contatoUpdateDTO);
        return new ResponseEntity<>(contatoAtualizado, HttpStatus.OK);
    }
}
