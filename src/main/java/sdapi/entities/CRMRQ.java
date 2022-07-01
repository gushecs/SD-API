package sdapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CRMRQ implements Serializable {

    @NotBlank(message = "O número do CRM é obrigatório!")
    @NotNull(message = "O número do CRM é obrigatório!")
    @Size(max = 45, message = "O campo pode conter no máximo 45 caracteres.")
    private String crm;

    @NotBlank(message = "A UF do estado é obrigatória!")
    @NotNull(message = "O número do CRM é obrigatório!")
    @Size(min = 2, max = 2, message = "Uma UF válida deve apresentar apenas 2 caracteres (Exemplo: \"SP\" para São Paulo).")
    private String uf;

    private String specialty;

    @NotNull(message = "O novo CRM deve ser associado à um usuário por meio de um campo \"user_id\"!")
    private Integer user_id;

}
