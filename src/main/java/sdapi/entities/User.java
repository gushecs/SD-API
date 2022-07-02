package sdapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
//    @SequenceGenerator(name = "user_id_sequence",sequenceName = "user_id_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer profile;

    @OneToMany(mappedBy = "user")
    private List<CRM> crms = new ArrayList<>();

    public User(UserRQ userRQ) {
        this.email=userRQ.getEmail();
        this.password=userRQ.getPassword();
        this.name=userRQ.getName();
        this.surname=userRQ.getSurname();
        this.mobile_phone=userRQ.getMobile_phone();
        this.authorization_status="UNREGISTERED";
        if (userRQ.getProfile() != null)
            this.profile=userRQ.getProfile();
        else
            this.profile = 2;
        for (CRMRQ crmRQ:userRQ.getCrms()){this.crms.add(new CRM(crmRQ));}
    }

}
