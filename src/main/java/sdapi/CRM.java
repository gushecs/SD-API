package sdapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CRM {

    @Id
    private Integer id;

    @Column(nullable = false, length = 45)
    private String crm;
    @Column(nullable = false, length = 2)
    private String uf;

    private String specialty;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
