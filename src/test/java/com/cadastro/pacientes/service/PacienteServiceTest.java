package com.cadastro.pacientes.service;


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

    @InjectMocks
    private PacienteService pacienteService;

    @Mock
    private PacienteRepository pacienteRepository;

    private Paciente paciente;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        paciente = Paciente.builder()
                .id(1L)
                .nome("Lucas")
                .cpf("12345678901")
                .idade(30)
                .build();
    }

    @Test
    void deveBuscarPacientePorIdComSucesso(){
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = pacienteService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Lucas", resultado.get().getNome());
    }
    @Test
    void deveSalvarPacienteComSucesso() {
        when(pacienteRepository.save(any())).thenReturn(paciente);

        Paciente salvo = pacienteService.salvar(paciente);

        assertNotNull(salvo);
        assertEquals("Lucas", salvo.getNome());
    }

    @Test
    void deveRetornarVazioAoBuscarIdInexistente() {
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Paciente> resultado = pacienteService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }
}
