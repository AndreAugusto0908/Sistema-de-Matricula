package br.com.pucminas.moedaestudantil.DTO;

import lombok.Builder;

@Builder
public record MailBodyDTO(String to, String subject, String text) {
}
