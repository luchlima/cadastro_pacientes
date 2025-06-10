package com.cadastro.pacientes.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório!")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "CPF é obrigatório!")
    @Size(min = 11, max = 11, message = "CPF deve conter 11 caracteres")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Min(value = 0, message = "Idade não pode ser negativa!")
    @Max(value = 130, message = "Idade inválida!")
    @Column(nullable = false)
    private Integer idade;
}
