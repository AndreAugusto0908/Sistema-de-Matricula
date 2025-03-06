package br.com.pucminas.matriculador2000.Controller;

import br.com.pucminas.matriculador2000.Models.Usuario;
import br.com.pucminas.matriculador2000.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }


    @PostMapping()
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Usuario user = service.getUsuario(username, password);
        String token = user.logIn();
        return ResponseEntity.ok(token);
    }
}
