package com.example.demo.service;

import com.example.demo.entity.Dentista;
import com.example.demo.repository.DentistaRepository;
import com.example.demo.service.interfaces.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaServiceImpl implements DentistaService {

    @Autowired
    private DentistaRepository dentistaRepository;

    @Override
    public Dentista criarDentista(Dentista dentista) {

        if (dentista.getNomeDentista() == null || dentista.getNomeDentista().isEmpty()) {
            throw new IllegalArgumentException("O nome do dentista é obrigatório.");
        }

        // Dentista destista  = new Dentista(dentistaDTO);
        return dentistaRepository.save(dentista);
    }


    @Override
    public Optional<Dentista> obterDentistaPorId(Long id) {
        return dentistaRepository.findById(id);
    }

    @Override
    public List<Dentista> listarDentistas() {
        return dentistaRepository.findAll();
    }

    @Override
    public Dentista atualizarDentista(Long id, Dentista dentista) {
        dentista.setIdDentista(id);
        return dentistaRepository.save(dentista);
    }

    @Override
    public void excluirDentista(Long id) {
        dentistaRepository.deleteById(id);
    }
}
