package ar.com.ada.api.empleadas.services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleadas.entities.Categoria;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repo;

    public void crearCategoria(Categoria categoria) {
        repo.save(categoria);  
    }

    public List<Categoria> traerCategorias() {
        return repo.findAll();   
    }
    
}
