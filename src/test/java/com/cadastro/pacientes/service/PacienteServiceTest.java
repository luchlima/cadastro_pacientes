package com.cadastro.pacientes.service;


import com.cadastro.pacientes.DTO.PacienteDTO;
import com.cadastro.pacientes.Entity.Paciente;
import com.cadastro.pacientes.Repository.PacienteRepository;
import com.cadastro.pacientes.Service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PacienteServiceTest {

    @InjectMocks // cria uma instancia real de pacienteService
    private PacienteService pacienteService;

    @Mock // cria um objeto simulado, para que possa controlar o comportamento no teste sem depender do BD real
    private PacienteRepository pacienteRepository;

    private Paciente paciente;

    @BeforeEach // --{
    void setUp(){ //                      -> inicia os mocks antes de cada teste, para garantir que sejam isolados
        MockitoAnnotations.openMocks(this);// --}

        paciente = Paciente.builder() // Cria um objeto para os testes
                .id(1L)
                .nome("Lucas")
                .cpf("12345678901")
                .idade(30)
                .build();
    }

    @Test
    void buscarCpfComSucesso(){
        final String cpf = "12345678901";

        Paciente pacienteTest = new Paciente();
        pacienteTest.setNome("Gabriel");
        pacienteTest.setCpf(cpf);

        when(pacienteRepository.findByCpf(cpf)).thenReturn(Optional.of(paciente));

        Optional<PacienteDTO> resultado = pacienteService.buscarDTOPorCpf("12345678901");

        assertTrue(resultado.isPresent());
        assertEquals("Lucas", resultado.get().getNome());
        assertEquals(cpf, resultado.get().getCpf());
    }

    @Test
    void dadosCpfInexistente(){
        final String cpf = "99999999999";
        when(pacienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        Optional<PacienteDTO> resultado = pacienteService.buscarDTOPorCpf(cpf);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveBuscarPacientePorIdComSucesso(){
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));// simula e retorna o paciente

        Optional<Paciente> resultado = pacienteService.buscarPorId(1L);// chama o metodo real do serviço que esta em teste

        assertTrue(resultado.isPresent()); // verifica se o paciente está presente
        assertEquals("Lucas", resultado.get().getNome()); // verifica se está correto
    }
    @Test
    void deveSalvarPacienteComSucesso() {
        when(pacienteRepository.save(any())).thenReturn(paciente);//simula que qualquer paciente salvo retorna o obj paciente

        Paciente salvo = pacienteService.salvar(paciente); //--{
        assertNotNull(salvo);// ---> verifica se o paciente foi salvo
        assertEquals("Lucas", salvo.getNome());//--}
    }

    @Test
    void deveRetornarVazioAoBuscarIdInexistente() {
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());// simula o id 99L que não existe

        Optional<Paciente> resultado = pacienteService.buscarPorId(99L);//--{
                                                    // -> verifica que o metodo retorna um optional vazio.
        assertFalse(resultado.isPresent()); //--}
    }
}
