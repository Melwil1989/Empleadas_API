package ar.com.ada.api.empleadas.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.empleadas.entities.Empleada;

public interface EmpleadaRepository extends JpaRepository<Empleada, Integer> {
    
}
