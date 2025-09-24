package co.com.pragma.model.loanapplication.dto;

import lombok.Builder;

@Builder
public record SQSMessage(

        String to,

        String subject,

        String body

){}
