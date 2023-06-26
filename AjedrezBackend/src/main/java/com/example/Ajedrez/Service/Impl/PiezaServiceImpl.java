package com.example.Ajedrez.Service.Impl;

import com.example.Ajedrez.Repository.PiezaRepository;
import com.example.Ajedrez.dominio.Pieza;
import org.springframework.stereotype.Service;

@Service
public class PiezaServiceImpl {
    private final PiezaRepository piezaRepository;

    public PiezaServiceImpl(PiezaRepository piezaRepository) {
        this.piezaRepository = piezaRepository;
    }

    public void guardarPieza(Pieza pieza) {
        piezaRepository.save(pieza);
    }
}
