package com.example.Ajedrez.Service.Impl;

import com.example.Ajedrez.Repository.TableroRepository;
import com.example.Ajedrez.dominio.Tablero;
import org.springframework.stereotype.Service;

@Service
public class TableroServiceRep {
    private final TableroRepository tableroRepository;

    public TableroServiceRep(TableroRepository tableroRepository) {
        this.tableroRepository = tableroRepository;
    }

    public Tablero guardarTablero(Tablero tablero) {
        return tableroRepository.save(tablero);
    }
}
