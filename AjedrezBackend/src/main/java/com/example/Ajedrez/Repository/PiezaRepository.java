package com.example.Ajedrez.Repository;

import com.example.Ajedrez.dominio.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PiezaRepository extends JpaRepository<Pieza, Long> {
}
