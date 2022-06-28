package sdapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CRMRegisterRQ implements Serializable {

    private String crm;
    private String uf;
    private String specialty;

}
