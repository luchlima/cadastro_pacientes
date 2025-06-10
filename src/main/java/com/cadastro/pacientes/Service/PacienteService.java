package com.cadastro.pacientes.Service;

import com.cadastro.pacientes.DTO.PacienteDTO;
import com.cadastro.pacientes.Entity.Paciente;
import com.cadastro.pacientes.Repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente fromDTO(PacienteDTO dto){
        return Paciente.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .idade(dto.getIdade())
                .build();
    }

    public PacienteDTO toDTO(Paciente paciente){
        PacienteDTO dto = new PacienteDTO();
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCpf());
        dto.setIdade(paciente.getIdade());
        return dto;
    }

    //metodo listar todos
    public List<Paciente> listarTodos(){
        return pacienteRepository.findAll();
    }

    //metodo listar por ID
    public Optional<Paciente> buscarPorId(Long id){
        return pacienteRepository.findById(id);
    }

    //metodo buscar por cpf
    public Optional<PacienteDTO> buscarDTOPorCpf(String cpf){
        return pacienteRepository.findByCpf(cpf)
                .map(this::toDTO);
    }

    //metodo para editar
    public Paciente atualizarPaciente(Long id, Paciente pacienteAtualizado){
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));
        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setCpf(pacienteAtualizado.getCpf());
        pacienteExistente.setIdade(pacienteAtualizado.getIdade());

        return pacienteRepository.save(pacienteExistente);
    }

    //metodo salvar
    public Paciente salvar(Paciente paciente){
        if (pacienteRepository.existsByCpf(paciente.getCpf())){
            throw new RuntimeException("CPF já cadastrado!");
        }
        return pacienteRepository.save(paciente);
    }

    //metodo deletar por ID
    public void deletarPorId(Long id){
        pacienteRepository.deleteById(id);
    }
}
