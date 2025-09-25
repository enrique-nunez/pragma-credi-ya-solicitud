package co.com.pragma.model.loanstatus;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loanstatus {
    private Long idStatus;
    private String name;
    private String description;
}
