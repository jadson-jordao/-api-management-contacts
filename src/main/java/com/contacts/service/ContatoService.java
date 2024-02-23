package com.contacts.service;

import com.contacts.dto.ContatoDTO;
import com.contacts.entity.Contato;
import com.contacts.repository.ContatoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ContatoDTO saveContato(ContatoDTO contatoDTO){
        Contato contato = modelMapper.map(contatoDTO, Contato.class);
        contato.getEnderecos().stream().forEach(end-> end.setContato(contato));
        contatoRepository.save(contato);
        return modelMapper.map(contato,ContatoDTO.class);
    }

    public ContatoDTO updateContato(ContatoDTO contatoDTO){
        Contato contato = modelMapper.map(contatoDTO, Contato.class);
        contatoRepository.save(contato);
        return modelMapper.map(contato,ContatoDTO.class);
    }

    public Optional<Contato> findById(Long idContato){
        return contatoRepository.findById(idContato);
    }

    public List<ContatoDTO> findAll(){
        return contatoRepository.findAll().stream().
                map(contato -> modelMapper.map(contato, ContatoDTO.class)).
                collect(Collectors.toList());
    }

    public boolean deleteContactById(Long idContact){
        Optional<Contato> contact = contatoRepository.findById(idContact);
        if(contact.isPresent()) {
            contatoRepository.deleteById(idContact);
            return true;
        }
        return false;
    }

    public void deleteAllContact(){
        contatoRepository.deleteAll();
    }
}
