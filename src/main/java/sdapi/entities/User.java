package sdapi.entities;

import lombok.*;
import sdapi.entities.CRM;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_user")
public class User {

    @Id()
    @SequenceGenerator(name = "user_id_sequence",sequenceName = "user_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    private String surname;
    private String mobile_phone;
    private String authorization_status;

    @OneToMany(mappedBy = "user")
    private List<CRM> crms = new ArrayList<>();

}
