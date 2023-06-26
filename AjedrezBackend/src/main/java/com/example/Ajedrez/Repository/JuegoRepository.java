package com.example.Ajedrez.Repository;

import com.example.Ajedrez.dominio.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface JuegoRepository extends JpaRepository<Juego, Long> {
    List<Juego> findAll();
    Optional<Juego> findById(Long id);

}
