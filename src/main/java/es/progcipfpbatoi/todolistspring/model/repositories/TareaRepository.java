package es.progcipfpbatoi.todolistspring.model.repositories;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Prioridad;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Repository
public class TareaRepository {

    private ArrayList<Tarea> tareas;

    public TareaRepository() {
        this.tareas = new ArrayList<>();
        this.tareas.add(new Tarea(
        		1, "Roberto", "Poner la mesa", 
        		Prioridad.ALTA, false, 
        		LocalDate.now(), LocalTime.now()));
    }

    /**
     * Añade la Tarea recibida como argumento a la base de datos en memoria
     * @param tarea
     */
    public void add(Tarea tarea) {
        this.tareas.add(tarea);
    }
    
    public void remove(Tarea tarea) {
        this.tareas.remove(tarea);
    }

    /**
     * Obtiene la Tarea con codigo @codTarea. En caso de que no la encuentre devolverá una excepción
     * @NotFoundException
     *
     * @param codTarea
     */
    public Tarea get(int codTarea) throws NotFoundException {
    	
    	for (Tarea tarea : tareas) {
			if (tarea.getCodigo() == codTarea) {
				return tarea;
			}
		}
    	
        throw new NotFoundException("La tarea con código " + codTarea + " no existe");
    }

    /**
     *  Devuelve el listado de todas las tareas.
     */
    public ArrayList<Tarea> findAll() {
        return tareas;
    }

    /**
     *  Devuelve el listado de todas las tareas cuyo atributo nombre coincide con @user
     */
    public ArrayList<Tarea> findAll(String user) throws NotFoundException{
        ArrayList<Tarea> tareasDeUsuario = new ArrayList<>();
        for (Tarea tarea : tareas) {
			if (tarea.getUsuario().equals(user)) {
				tareasDeUsuario.add(tarea);
			}
		}
        
        if (tareasDeUsuario.isEmpty()) {
        	throw new NotFoundException("No hay tareas de " + user);
        }
        return tareasDeUsuario;
        
    }

}
