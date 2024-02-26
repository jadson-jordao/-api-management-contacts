package com.contacts.service;

import com.contacts.dto.ContatoCreateDTO;
import com.contacts.dto.ContatoResponseDTO;
import com.contacts.dto.ContatoUpdateDTO;
import com.contacts.entity.Contato;
import com.contacts.exception.ContatoNotFoundException;
import com.contacts.repository.ContatoRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ContatoResponseDTO saveContact(ContatoCreateDTO contatoCreateDTO){
        log.info("::Iniciando metodo de salvar contato.");
        Contato contato = modelMapper.map(contatoCreateDTO, Contato.class);
        contato.getEnderecos().forEach(end-> end.setContato(contato));
        contatoRepository.save(contato);
        log.info("::Contato foi salvo com sucesso.");
        return modelMapper.map(contato, ContatoResponseDTO.class);
    }

    public ContatoResponseDTO updateContactPut(Long idContact, ContatoUpdateDTO contatoUpdateDTO){
        log.info("::Iniciando metodo de atualização de contato pelo metodo PUT.");
        Optional<Contato> contact = contatoRepository.findById(idContact);
        if(!contact.isPresent() || Objects.isNull(contatoUpdateDTO))
            throw new ContatoNotFoundException();

        Contato contato = modelMapper.map(contatoUpdateDTO, Contato.class);
        contato.setIdContato(idContact);
        contato.getEnderecos().forEach(endereco -> endereco.setContato(contato));
        Contato contatoAtualizado = contatoRepository.save(contato);
        log.info("::Contato atualizado com sucesso pelo metodo PUT.");
        return modelMapper.map(contatoAtualizado, ContatoResponseDTO.class);
    }
    public ContatoResponseDTO updateContactPatch(Long idContact, ContatoUpdateDTO contatoUpdateDTO){
        log.info("::Iniciando metodo de atualização de contato pelo metodo PATCH.");
        Optional<Contato> contact = contatoRepository.findById(idContact);
        if(!contact.isPresent() || Objects.isNull(contatoUpdateDTO))
            throw new ContatoNotFoundException();

        Contato contato = getContactUpdated(contatoUpdateDTO, contact);
        contatoUpdateDTO.getEnderecos().forEach(
                enderecoDTO -> contato.getEnderecos().stream()
                        .filter(endereco -> Objects.equals(endereco.getIdEndereco(),enderecoDTO.getIdEndereco()))
                        .forEach(endereco -> {
                            if(Objects.nonNull(enderecoDTO.getRua()))
                                endereco.setRua(enderecoDTO.getRua());
                            if(Objects.nonNull(enderecoDTO.getNumero()))
                                endereco.setNumero(enderecoDTO.getNumero());
                            if(Objects.nonNull(enderecoDTO.getCep()))
                                endereco.setCep(enderecoDTO.getCep());
                        })
        );
        Contato contatoAtualizado = contatoRepository.save(contato);
        log.info("::Contato atualizado com sucesso pelo metodo PATCH.");
        return modelMapper.map(contatoAtualizado, ContatoResponseDTO.class);
    }

    private static Contato getContactUpdated(ContatoUpdateDTO contatoUpdateDTO, Optional<Contato> contact) {
        Contato contato = contact.get();
        if(Objects.nonNull(contatoUpdateDTO.getNome()))
            contato.setNome(contatoUpdateDTO.getNome());
        if(Objects.nonNull(contatoUpdateDTO.getEmail()))
            contato.setEmail(contatoUpdateDTO.getEmail());
        if(Objects.nonNull(contatoUpdateDTO.getDataNascimento()))
            contato.setDataNascimento(contatoUpdateDTO.getDataNascimento());
        if(Objects.nonNull(contatoUpdateDTO.getTelefone()))
            contato.setTelefone(contatoUpdateDTO.getTelefone());
        return contato;
    }

    public ContatoResponseDTO findContactById(Long idContato){
        log.info("::Requisição do metodo GET, busca contato por id.");
        Optional<Contato> contact = contatoRepository.findById(idContato);
        if(!contact.isPresent())
            throw new ContatoNotFoundException();

        Contato contato = contact.get();
        log.info("::Requisição do metodo GET, busca contato por id executada com sucesso.");
        return modelMapper.map(contato, ContatoResponseDTO.class);
    }

    public List<ContatoResponseDTO> findAllContact(){
        log.info("::Requisição do metodo GET, busca todos os contatos cadastrados.");
        return contatoRepository.findAll().stream().
                map(contato -> modelMapper.map(contato, ContatoResponseDTO.class)).
                collect(Collectors.toList());
    }

    public void deleteContactById(Long idContact){
        log.info("::Requisição do metodo DELETE, deleta contato por id.");
        Optional<Contato> contact = contatoRepository.findById(idContact);
        if(!contact.isPresent())
            throw new ContatoNotFoundException();
        contatoRepository.deleteById(idContact);
        log.info("::Requisição do metodo DELETE, exclusão por id realizada com sucesso.");
    }

    public void deleteAllContact() {
        log.info("::Requisição do metodo DELETE, deleta todos os contatos.");
        if (contatoRepository.findAll().isEmpty())
            throw new ContatoNotFoundException();

        contatoRepository.deleteAll();;
        log.info("::Requisição do metodo DELETE, exclusão realizada com sucesso.");
    }
}
