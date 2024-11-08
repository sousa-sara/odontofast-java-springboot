package com.example.demo.controller;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Dentista;
import com.example.demo.entity.Notificacao;
import com.example.demo.entity.PlanoDeSaude;
import com.example.demo.entity.Usuario;
import com.example.demo.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(converterDtoParaEntidade(usuarioDTO));
            UsuarioDTO resource = converterEntidadeParaDto(novoUsuario);
            adicionarLinks(resource);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = Optional.ofNullable(usuarioService.obterUsuarioPorId(id));
        return usuarioOptional.map(usuario -> {
            UsuarioDTO resource = converterEntidadeParaDto(usuario);
            adicionarLinks(resource);
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioDTO> usuarioDTOs = usuarios.stream()
                .map(this::converterEntidadeParaDto)
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarioDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, converterDtoParaEntidade(usuarioDTO));
        UsuarioDTO resource = converterEntidadeParaDto(usuarioAtualizado);
        adicionarLinks(resource);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(UsuarioDTO usuarioDTO) {
        usuarioDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuario(usuarioDTO.getIdUsuario())).withSelfRel());
        usuarioDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).atualizarUsuario(usuarioDTO.getIdUsuario(), usuarioDTO)).withRel("atualizar"));
        usuarioDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withRel("listarUsuarios"));
        usuarioDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).excluirUsuario(usuarioDTO.getIdUsuario())).withRel("excluir"));
    }

    private Usuario converterDtoParaEntidade(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioDTO.getIdUsuario());
        usuario.setNomeUsuario(usuarioDTO.getNomeUsuario());
        usuario.setSenhaUsuario(usuarioDTO.getSenhaUsuario());
        usuario.setEmailUsuario(usuarioDTO.getEmailUsuario());
        usuario.setNrCarteira(usuarioDTO.getNrCarteira());

        if (usuarioDTO.getDentistaIdDentista() != null) {
            usuario.setDentista(new Dentista(usuarioDTO.getDentistaIdDentista()));
        }
        if (usuarioDTO.getPlanoIdPlano() != null) {
            usuario.setPlanoDeSaude(new PlanoDeSaude(usuarioDTO.getPlanoIdPlano()));
        }
        if (usuarioDTO.getNotIdNotificacao() != null) {
            usuario.setNotificacao(new Notificacao(usuarioDTO.getNotIdNotificacao()));
        }

        return usuario;
    }

    // Conversão de Entidade para DTO
    private UsuarioDTO converterEntidadeParaDto(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setNomeUsuario(usuario.getNomeUsuario());
        usuarioDTO.setSenhaUsuario(usuario.getSenhaUsuario());
        usuarioDTO.setEmailUsuario(usuario.getEmailUsuario());
        usuarioDTO.setNrCarteira(usuario.getNrCarteira());

        if (usuario.getDentista() != null) {
            usuarioDTO.setDentistaIdDentista(usuario.getDentista().getIdDentista());
        }
        if (usuario.getPlanoDeSaude() != null) {
            usuarioDTO.setPlanoIdPlano(usuario.getPlanoDeSaude().getIdPlano());
        }
        if (usuario.getNotificacao() != null) {
            usuarioDTO.setNotIdNotificacao(usuario.getNotificacao().getIdNotificacao());
        }

        return usuarioDTO;
    }
}
