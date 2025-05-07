package es.progcipfpbatoi.todolistspring.controllers;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Prioridad;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import es.progcipfpbatoi.todolistspring.model.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @GetMapping("/tarea-form")
    public String tareaFormActionView(){
        return "tarea_form_view";
    }
    
    @GetMapping("/tarea-find")
    @ResponseBody
    public String getTarea(@RequestParam int codigo) {
    	
    	try {
    		Tarea tarea = tareaRepository.get(codigo);
    		return tarea.toString();
    		
    	}catch(NotFoundException ex) {
    		return "<html>"+ ex.getMessage()+"</html>";
    	}
    }

    @PostMapping("/tarea-add")
    @ResponseBody
    public String postAddAction(@RequestParam Map<String, String> params) {
    	
    	try {
	        int code = Integer.parseInt(params.get("code"));
	        String user = params.get("user");
	        String descripcion = params.get("description");
	        
	        Prioridad prioridad = Prioridad.fromString(
	        		params.get("prioridad"));
	        
	        String realizada = params.get("realizada");
	        
	        boolean realizadaBoolean = realizada!= null 
	        		&& realizada.equals("true");
	        
	        String fechaVencimiento = params.get("fechaVenc");
	        DateTimeFormatter formatter = 
	        		DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate fecha = LocalDate.parse(fechaVencimiento, formatter);
	        
	        String horaVencimiento = params.get("horaVenc");
	        DateTimeFormatter timeFormatter = 
	        		DateTimeFormatter.ofPattern("HH:mm");
	        LocalTime hora = LocalTime.parse(horaVencimiento, timeFormatter);
	        
	        Tarea tarea = new Tarea(
	        		code, user, descripcion, 
	        		prioridad, realizadaBoolean, fecha, hora);
	        
	        tareaRepository.add(tarea);
	        return "<html>" +
	                "<body>Tarea " + tarea.getCodigo() + " recibida con éxito</body>" +
	                "</html>";
    	} catch(NotFoundException ex) {
    		return "<html><body>Error de procesamiento:"
    				+ "La tarea no ha "
    				+ "podido ser creada</body></html>";
    	}
    }
    
    @GetMapping("/tarea")
    public String showTarea(
    		@RequestParam int codTarea,
    		Model model) {
    	try {
	    	Tarea tarea = tareaRepository.get(codTarea);
	    	model.addAttribute("tarea", tarea);
	    	return "tarea_details_view";
    	} catch(NotFoundException ex) {
    		return "error";
    	}
    }

}
