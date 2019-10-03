package mx.test.example.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import mx.test.example.app.models.entity.Organizaciones;
import mx.test.example.app.service.IClienteService;


@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@Value("${application.controller.titulo}")
	String titulo;

	@GetMapping("/")
	public String home1(Model model) {

		String regresa = "/login";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!principal.toString().equalsIgnoreCase("anonymousUser")) {
			
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Collection<GrantedAuthority> auth = user.getAuthorities();

			for (GrantedAuthority authority : auth) {
				String hasRole = authority.getAuthority();

				if (hasRole.equalsIgnoreCase("ROLE_ADMIN")) {
					regresa = "redirect:/listar";
				}
				if (hasRole.equalsIgnoreCase("ROLE_USER")) {
					regresa = "/user";
				}
			}

		}

		model.addAttribute("titulo", this.titulo);

		return regresa;
	}


	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) throws Exception {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Organizaciones cliente = new Organizaciones();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "form";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		
		Organizaciones cliente = null;
		
		if(id > 0) {
			cliente = clienteService.findOne(id);
		} else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Organizaciones org, BindingResult result, Model model, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		String idExterno = clienteService.generaIdExterno(org.getNombre(), org.getTelefono());
		String codigoEncriptado = clienteService.codigoEncriptacion(org.getNombre());
				

		org.setId_externo(idExterno);
		org.setCodigo_encriptacion(codigoEncriptado);

		clienteService.save(org);
		status.setComplete();
		return "redirect:listar";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		
		if(id > 0) {
			clienteService.delete(id);
		}
		return "redirect:/listar";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("titulo", this.titulo);

		return "/login";
	}

	@GetMapping("/403")
	public String error403(Model model) {
		model.addAttribute("titulo", this.titulo);

		return "/error/403";
	}
}
