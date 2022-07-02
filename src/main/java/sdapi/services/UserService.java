package sdapi.services;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import sdapi.entities.CRM;
import sdapi.entities.User;
import sdapi.repositories.CRMRepository;
import sdapi.repositories.UserRepository;

import java.util.List;

@Service
public record UserService (CRMRepository crmRepository, UserRepository userRepository){

    public List<User> findAll(String name, String specialty){return userRepository.findAll(name, specialty);}

    public User findById(Integer id){return userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id,id.toString()));}

    public User findByEmailAndPassword(String email, String password){return userRepository.findByEmailAndPassword(email, password);};

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
            if (crmRepository.findByCrmAndUf(crm.getCrm(), crm.getUf().getDescription()).isPresent()) {
                CRM crmToUpdate = crmRepository.findByCrmAndUf(crm.getCrm(), crm.getUf().getDescription()).get();
                crmToUpdate.setUser(user);
                crmRepository.save(crmToUpdate);
            } else {
                crm.setUser(user);
                crmRepository.save(crm);
            }
        }
    }

}
