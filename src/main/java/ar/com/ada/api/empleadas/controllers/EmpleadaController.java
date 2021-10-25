package ar.com.ada.api.empleadas.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.empleadas.entities.Categoria;
import ar.com.ada.api.empleadas.entities.Empleada;
import ar.com.ada.api.empleadas.entities.Empleada.EstadoEmpleadaEnum;
import ar.com.ada.api.empleadas.models.request.InfoEmpleadaNueva;
import ar.com.ada.api.empleadas.models.request.SueldoNuevoEmpleada;
import ar.com.ada.api.empleadas.models.response.GenericResponse;
import ar.com.ada.api.empleadas.services.CategoriaService;
import ar.com.ada.api.empleadas.services.EmpleadaService;


@RestController
public class EmpleadaController {

    @Autowired
    private EmpleadaService service;

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleada>> traerEmpleadas() {
        List<Empleada> lista = service.traerEmpleadas();

        return ResponseEntity.ok(lista);
    }
    
    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleada(@RequestBody InfoEmpleadaNueva empleadaInfo) {
        GenericResponse respuesta = new GenericResponse();

        Empleada empleada = new Empleada(empleadaInfo.nombre, empleadaInfo.edad, empleadaInfo.sueldo, new Date());

        Categoria categoria = categoriaService.buscarCategoria(empleadaInfo.categoriaId);
        empleada.setCategoria(categoria);
        empleada.setEstado(EstadoEmpleadaEnum.ACTIVO);

        service.crearEmpleada(empleada);
        respuesta.isOk = true;
        respuesta.id = empleada.getEmpleadaId();
        respuesta.message = "La empleada fue creada con exito";

        return ResponseEntity.ok(respuesta);
        
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleada> getEmpleadaPorId(@PathVariable Integer id) {  // si no se pone @PathVariable nunca va a mapear al
                                                                                  // valor que esta entre llaves
        Empleada empleada = service.buscarEmpleada(id);

        if(empleada == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empleada);
    }

    // delete/empleados/{id} ---> da de baja un empleado poniendo el campo estado en "baja"
    // y la fecha de baja que sea la actual
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleada(@PathVariable Integer id) {
         
        service.bajaEmpleadaPorId(id);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "La empleada fue dada de baja con exito";

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/empleados/categorias/{catId}")
    public ResponseEntity<List<Empleada>> obtenerEmpleadasPorCategoria(@PathVariable Integer catId) {

        List<Empleada> empleadas = service.traerEmpleadaPorCategoria(catId);
        return ResponseEntity.ok(empleadas);
    }

    @PutMapping("empleados/{id}/sueldos")
    public ResponseEntity<GenericResponse> modificarSueldo(@PathVariable Integer id, @RequestBody SueldoNuevoEmpleada sueldoNuevoInfo) {
        
        Empleada empleada = service.buscarEmpleada(id);

        empleada.setSueldo(sueldoNuevoInfo.sueldoNuevo);

        service.guardar(empleada);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "Sueldo actualizado";

        return ResponseEntity.ok(respuesta);
    }
    
}
