package sdapi;

import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record UserService (CRMService crmService, UserRepository userRepository){

    public List<User> findAll(String name, String specialty){
        List<CRM> crms= new ArrayList<>();
        crms.add(new CRM(null,null,null,specialty,null));
        return userRepository.findAll(Example.of(User.builder().name(name).crms(crms).build()));}

    public User findById(Integer id){return userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id,id.toString()));}

    public User signUp(User user){
        User userToReturn = userRepository.save(user);
        checkCRMs(user);
        return userToReturn;}

    public void delete(Integer id){userRepository.deleteById(id);}

    public User update(Integer id, User user) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setMobile_phone(user.getMobile_phone());
        userToUpdate.setCrms(user.getCrms());
        checkCRMs(userToUpdate);
        userToUpdate.setAuthorization_status(user.getAuthorization_status());
        return userRepository.save(userToUpdate);}

    private void checkCRMs(User user) {
        for (CRM crm: user.getCrms()){
            if (crmService.findByCrmAndUf(crm.getCrm(), crm.getUf().getDescription()).isPresent()) {
                CRM crmToUpdate = crmService.findByCrmAndUf(crm.getCrm(), crm.getUf().getDescription()).get();
                crmToUpdate.setUser(user);
                crmService.register(crmToUpdate);
            } else {
                crm.setUser(user);
                crmService.register(crm);
            }
        }
    }

}
