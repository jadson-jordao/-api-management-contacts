package com.contacts.service;

import com.contacts.dto.ContatoDTO;
import com.contacts.entity.Contato;
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

    public ContatoDTO saveContact(ContatoDTO contatoDTO){
        log.info("::Iniciando metodo de salvar contato.");
        Contato contato = modelMapper.map(contatoDTO, Contato.class);
        contato.getEnderecos().forEach(end-> end.setContato(contato));
        contatoRepository.save(contato);
        log.info("::Contato foi salvo com sucesso.");
        return modelMapper.map(contato,ContatoDTO.class);
    }

    public ContatoDTO updateContactPut(Long idContact, ContatoDTO contatoDTO){
        log.info("::Iniciando metodo de atualização de contato pelo metodo PUT.");
        Optional<Contato> contact = contatoRepository.findById(idContact);
        if(!contact.isPresent())
            return null;

        Contato contato = contact.get();
        contato.setNome(contatoDTO.getNome());
        contato.setEmail(contatoDTO.getEmail());
        contato.setDataNascimento(contatoDTO.getDataNascimento());
        contato.setTelefone(contatoDTO.getTelefone());
        contatoDTO.getEnderecos().forEach(
                enderecoDTO -> contato.getEnderecos().stream()
                        .filter(endereco -> Objects.equals(endereco.getIdEndereco(),enderecoDTO.getIdEndereco()))
                        .forEach(endereco -> {
                            endereco.setRua(enderecoDTO.getRua());
                            endereco.setNumero(enderecoDTO.getNumero());
                            endereco.setCep(enderecoDTO.getCep());
                        })
        );
        Contato contatoAtualizado = contatoRepository.save(contato);
        log.info("::Contato atualizado com sucesso pelo metodo PUT.");
        return modelMapper.map(contatoAtualizado,ContatoDTO.class);
    }
    public ContatoDTO updateContactPatch(Long idContact, ContatoDTO contatoDTO){
        log.info("::Iniciando metodo de atualização de contato pelo metodo PATCH.");
        Optional<Contato> contact = contatoRepository.findById(idContact);
        if(!contact.isPresent())
            return null;

        Contato contato = contact.get();
        if(Objects.nonNull(contatoDTO.getNome()))
            contato.setNome(contatoDTO.getNome());
        if(Objects.nonNull(contatoDTO.getEmail()))
            contato.setEmail(contatoDTO.getEmail());
        if(Objects.nonNull(contatoDTO.getDataNascimento()))
            contato.setDataNascimento(contatoDTO.getDataNascimento());
        if(Objects.nonNull(contatoDTO.getTelefone()))
            contato.setTelefone(contatoDTO.getTelefone());
        contatoDTO.getEnderecos().forEach(
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
        return modelMapper.map(contatoAtualizado,ContatoDTO.class);
    }

    public ContatoDTO findContactById(Long idContato){
        log.info("::Requisição do metodo GET, busca contato por id.");
        Optional<Contato> contact = contatoRepository.findById(idContato);
        if(!contact.isPresent())
            return null;

        Contato contato = contact.get();
        log.info("::Requisição do metodo GET, busca contato por id executada com sucesso.");
        return modelMapper.map(contato,ContatoDTO.class);
    }

    public List<ContatoDTO> findAllContact(){
        log.info("::Requisição do metodo GET, busca todos os contatos cadastrados.");
        return contatoRepository.findAll().stream().
                map(contato -> modelMapper.map(contato, ContatoDTO.class)).
                collect(Collectors.toList());
    }

    public boolean deleteContactById(Long idContact){
        log.info("::Requisição do metodo DELETE, deleta contato por id.");
        Optional<Contato> contact = contatoRepository.findById(idContact);
        if(!contact.isPresent())
            return false;
        contatoRepository.deleteById(idContact);
        log.info("::Requisição do metodo DELETE, exclusão por id realizada com sucesso.");
        return true;
    }

    public boolean deleteAllContact() {
        log.info("::Requisição do metodo DELETE, deleta todos os contatos.");
        if (contatoRepository.findAll().isEmpty())
            return false;

        contatoRepository.deleteAll();;
        log.info("::Requisição do metodo DELETE, exclusão realizada com sucesso.");
        return true;
    }
}
