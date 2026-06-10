package com.locafy.locafy_backend.security;


import com.locafy.locafy_backend.model.Usuario;
import com.locafy.locafy_backend.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.locafy.locafy_backend.security.SecurityConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales invalidas.");
        }

        String token = jwtUtil.crearToken(
                usuario.getEmail(),
                usuario.getRol().name()
        );

        return ResponseEntity.ok(new AuthResponse(
                token,
                usuario.getId(),
                usuario.getEmail(),
                usuario.getRol().name()
        ));
    }
}
