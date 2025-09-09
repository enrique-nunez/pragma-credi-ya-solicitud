package co.com.pragma.model.loanapplication.dto;

import lombok.Data;

@Data
public class SearchRequest {
    Integer page = 0;
    Integer size = 10;
}
