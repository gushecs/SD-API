package sdapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UF {

    RO(1,"RO"), AC(2,"AC"), AM(3,"AM"), RR(4,"RR"), PA(5,"PA"), AP(6,"AP"),
    TO(7,"TO"), MA(8,"MA"), PI(9,"PI"), CE(10,"CE"), RN(11,"RN"), PB(12,"PB"),
    PE(13,"PE"), AL(14,"AL"), SE(15,"SE"), BA(16,"BA"), MG(17,"MG"),
    ES(18,"ES"), RJ(19,"RJ"), SP(20,"SP"), PR(21,"PR"), SC(22,"SC"),
    RS(23,"RS"), MS(24,"MS"), MT(25,"MT"), GO(26,"GO"), DF(27,"DF");

    private final int cod;
    private final String description;

    public static UF toEnum(String uf) {
        if (uf==null) {
            return null;
        }
        for (UF x : UF.values()) {
            if (uf.equals(x.getDescription())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid UF "+uf);
    }

}
