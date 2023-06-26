package com.example.Ajedrez.Repository;

import com.example.Ajedrez.dominio.Tablero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableroRepository extends JpaRepository<Tablero, Long> {
}