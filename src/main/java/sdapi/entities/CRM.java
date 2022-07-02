package sdapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"crm","uf"})})
public class CRM {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String crm;
    @Column(nullable = false, length = 2)
    private String uf;

    private String specialty;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    public void setUf(UF uf) {this.uf = uf.getDescription();}

    public UF getUf(){return UF.toEnum(this.uf);}

    public CRM(CRMRQ crmRQ) {
        this.crm=crmRQ.getCrm();
        this.uf=UF.toEnum(crmRQ.getUf()).getDescription();
        this.specialty=crmRQ.getSpecialty();
        this.user=null;
    }
}
