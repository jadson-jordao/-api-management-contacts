package com.contacts.service;

import com.contacts.dto.ContatoResponseDTO;
import com.contacts.dto.ContatoUpdateDTO;
import com.contacts.entity.Contato;
import com.contacts.entity.Endereco;
import com.contacts.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContatoServiceTeste {

    @InjectMocks
    private ContatoService contatoService;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ModelMapper modelMapper;

    private Contato contato;
    private ContatoResponseDTO contatoResponseDTO;

    @BeforeEach
    public void setUp(){
        contato =  Contato.builder()
                .nome("Person test")
                .idContato(1L)
                .email("teste@teste.com")
                .dataNascimento(new Date())
                .telefone("+5537999000000")
                .enderecos(Collections.singletonList(Endereco.builder()
                        .idEndereco(1l)
                        .cep(35680000)
                        .rua("Rua das Flores")
                        .build()))
                .build();

    }

    @Test
    void deveCarregarContatoPorIdentificador(){
        when(contatoRepository.findById(contato.getIdContato())).thenReturn(Optional.of(contato));
        ContatoResponseDTO contatoResponseDTO = contatoService.findContactById(contato.getIdContato());
        assertEquals(modelMapper.map(contato, ContatoResponseDTO.class),contatoResponseDTO);
        /*verifica se repository foi chamado mais de uma vez*/
        verify(contatoRepository).findById(contato.getIdContato());
        /*verifica se repository não foi chamado mais de uma vez*/
        verifyNoMoreInteractions(contatoRepository);
    }

    @Test
    void naoDeveCarregarContatoCasoIdSejaNull(){
        ContatoResponseDTO contatoResponseDTO = contatoService.findContactById(null);
        assertNull(contatoResponseDTO);
        assertEquals(modelMapper.map(contato, ContatoResponseDTO.class),contatoResponseDTO);
        /*verifica se repository foi chamado mais de uma vez*/
        verify(contatoRepository).findById(null);
    }
    @Test
    void casoNaoSejaPassadoIdNaoAtualizaContato(){
        ContatoResponseDTO contatoResponseDTO = contatoService.updateContactPut(
                null, modelMapper.map(contato, ContatoUpdateDTO.class));
        assertNull(contatoResponseDTO);
        /*verifica se repository foi chamado mais de uma vez*/
        verify(contatoRepository).findById(null);
    }
    @Test
    void casoSejaPassadoIdAndNaoSejaPassadoBodyNaoAtualizaContato(){
        ContatoResponseDTO contatoResponseDTO = contatoService.updateContactPut(
                contato.getIdContato(), null);
        assertNull(contatoResponseDTO);
        /*verifica se repository foi chamado mais de uma vez*/
        verify(contatoRepository).findById(contato.getIdContato());
    }

    @Test
    void casoSejaPassadoIdAndBodyAtualizaContato(){

        contato.setTelefone("123456789");
        contato.setEmail("emailalterado@hotmail.com");

        when(contatoRepository.save(contato)).thenReturn(contato);

        ContatoResponseDTO contatoResponseDTO = contatoService.updateContactPut(
                contato.getIdContato(), modelMapper.map(contato, ContatoUpdateDTO.class));
        assertNull(contatoResponseDTO);
        /*verifica se repository foi chamado mais de uma vez*/
        verify(contatoRepository).findById(contato.getIdContato());
    }

}
