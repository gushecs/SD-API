package sdapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRQ {

    @NotNull(message = "O campo de email é obrigatório!")
    @NotEmpty(message = "O campo de email é obrigatório!")
    @Email(message = "Email inválido!")
    private String email;

    @NotNull(message = "Você precisa cadastrar uma senha.")
    @NotEmpty(message = "Você precisa cadastrar uma senha.")
    private String password;

    @NotNull(message = "O campo de nome é obrigatório!")
    @NotEmpty(message = "O campo de nome é obrigatório!")
    private String name;

    private String surname;
    private String mobile_phone;

    @Min(value = 1, message = "Profile inválido! Possíveis valores: 1 para conta Admin e 2 para usuário padrão.")
    @Max(value = 2, message = "Profile inválido! Possíveis valores: 1 para conta Admin e 2 para usuário padrão.")
    private Integer profile;
    private List<CRMRQ> crms = new ArrayList<>();

}
