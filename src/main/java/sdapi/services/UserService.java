package sdapi.services;

import org.springframework.stereotype.Service;
import sdapi.controllers.exceptions.ObjectNotFoundException;
import sdapi.entities.CRM;
import sdapi.entities.User;
import sdapi.entities.UserRQ;
import sdapi.entities.UserRS;
import sdapi.repositories.CRMRepository;
import sdapi.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public record UserService (CRMRepository crmRepository, UserRepository userRepository){

    public List<UserRS> findAll(String name, String specialty){
        List<UserRS> usersToReturn = new ArrayList<>();
        userRepository.findAll(name, specialty).forEach(x -> usersToReturn.add(new UserRS(x)));
        return usersToReturn;}

    public UserRS findById(Integer id){return new UserRS(userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Não há usuários para o ID especificado!")));}

    public User findByEmail(String email){return userRepository.findByEmail(email);}

    public UserRS signUp(UserRQ userRQ){
        if (userRepository.findByEmail(userRQ.getEmail()) != null)
            throw new IllegalArgumentException("Email já cadastrado em outro usuário!");
        User user = userRepository.save(new User(userRQ));
        checkCRMs(user);
        return new UserRS(user);}

    public void delete(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Não há usuários para o ID especificado!"));
        crmRepository.deleteAll(user.getCrms());
        userRepository.deleteById(id);}

    public UserRS update(Integer id, UserRQ userRQ) {

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Não há usuários para o ID especificado!"));
        if (userRepository.findByEmail(userRQ.getEmail()) != null && !Objects.equals(userRepository.findByEmail(userRQ.getEmail()).getId(), id))
            throw new IllegalArgumentException("Email já cadastrado em outro usuário!");

        User user = new User(userRQ);

        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setMobile_phone(user.getMobile_phone());
        userToUpdate.setAuthorization_status(user.getAuthorization_status());
        userToUpdate.setProfile(user.getProfile());

        List<CRM> oldCrms = userToUpdate.getCrms();
        userToUpdate.setCrms(user.getCrms());
        for (CRM crm:oldCrms){ // Check if any old CRM lost it's owner, if so drops it
            if (userToUpdate.getCrms().stream().noneMatch(x -> x.getCrm().equals(crm.getCrm()) && x.getUf().equals(crm.getUf()))) {
                crmRepository.delete(crm);
            }
        }

        checkCRMs(userToUpdate);
        return new UserRS(userRepository.save(userToUpdate));}

    private void checkCRMs(User user) {
        List<CRM> crms = user.getCrms();
        for (CRM crm: crms){
            if (crmRepository.findByCrmAndUf(crm.getCrm(), crm.getUf().getDescription()).isPresent()) {
                CRM crmToUpdate = crmRepository.findByCrmAndUf(crm.getCrm(), crm.getUf().getDescription()).get();
                crmToUpdate.setUser(user);
                crmToUpdate.setSpecialty(crm.getSpecialty());
                crm.setId(crmToUpdate.getId());
                crmRepository.save(crmToUpdate);
            } else {
                crm.setUser(user);
                CRM crmSaved = crmRepository.save(crm);
                crm.setId(crmSaved.getId());
            }
        }
        user.setCrms(crms);
    }

}
