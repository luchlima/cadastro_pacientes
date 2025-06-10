package com.cadastro.pacientes.Controller;

import com.cadastro.pacientes.DTO.PacienteDTO;
import com.cadastro.pacientes.Entity.Paciente;
import com.cadastro.pacientes.Service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping()
    public ResponseEntity<PacienteDTO> criar(@RequestBody @Valid PacienteDTO pacienteDTO){
        Paciente paciente = pacienteService.fromDTO(pacienteDTO);
        Paciente salvo = pacienteService.salvar(paciente);

        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.toDTO(salvo));
    }

    @GetMapping()
    public ResponseEntity<List<Paciente>> listarTodos(){
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPorId(@PathVariable Long id){
       return pacienteService.buscarPorId(id)
               .map(paciente -> ResponseEntity.ok(pacienteService.toDTO(paciente)))
               .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PacienteDTO> buscarPorCpf(@PathVariable String cpf){
        return pacienteService.buscarDTOPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO){
        try {
            Paciente atualizado = pacienteService.atualizarPaciente(id, pacienteService.fromDTO(pacienteDTO));
            return ResponseEntity.ok(pacienteService.toDTO(atualizado));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        pacienteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
