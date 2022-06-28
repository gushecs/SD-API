package sdapi;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserService (UserRepository userRepository){

    public List<User> findAll(){return userRepository.findAll();}

    public User findById(Integer id){return userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id,id.toString()));}

    public User signUp(User user){return userRepository.save(user);}

    public void delete(Integer id){userRepository.deleteById(id);}

    public User update(Integer id, User user) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setMobile_phone(user.getMobile_phone());
        userToUpdate.setCrms(user.getCrms());
        userToUpdate.setAuthorization_status(user.getAuthorization_status());
        return userRepository.save(userToUpdate);}

}
