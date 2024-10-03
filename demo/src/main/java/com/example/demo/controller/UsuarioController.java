package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Dentista;
import com.example.demo.entity.Notificacao;
import com.example.demo.entity.PlanoDeSaude;
import com.example.demo.entity.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = converterDtoParaEntidade(usuarioDTO);
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        UsuarioDTO novoUsuarioDTO = converterEntidadeParaDto(novoUsuario);
        return ResponseEntity.ok(novoUsuarioDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obterUsuarioPorId(id);
        UsuarioDTO usuarioDTO = converterEntidadeParaDto(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(this::converterEntidadeParaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = converterDtoParaEntidade(usuarioDTO);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        UsuarioDTO usuarioAtualizadoDTO = converterEntidadeParaDto(usuarioAtualizado);
        return ResponseEntity.ok(usuarioAtualizadoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Conversão de DTO para Entidade
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
