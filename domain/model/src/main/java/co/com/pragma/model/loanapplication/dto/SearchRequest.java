package co.com.pragma.model.loanapplication.dto;

import lombok.Data;

@Data
public class SearchRequest {
    Integer statusId;
    Integer page;
    Integer size;
}
