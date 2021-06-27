package ar.com.ada.api.empleadas.services;

import ar.com.ada.api.empleadas.entities.Empleada;
import ar.com.ada.api.empleadas.repos.EmpleadaRepository;

public class EmpleadaService {

    EmpleadaRepository repo;

    public void crearEmpleada(Empleada empleada) {
        repo.save(empleada);

    }
    
}
