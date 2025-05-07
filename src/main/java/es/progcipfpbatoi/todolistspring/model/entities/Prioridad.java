package es.progcipfpbatoi.todolistspring.model.entities;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;

public enum Prioridad {
	ALTA, MEDIA, BAJA;
	
	public static Prioridad fromString(String prioridadText) 
			throws NotFoundException{
		
		switch(prioridadText.toLowerCase()) {
			case "alta":
				return ALTA; 
			case "media":
				return MEDIA;
			case "baja":
				return BAJA;
			default:
				throw new NotFoundException("Prioridad no existente");
		}
	}
}
