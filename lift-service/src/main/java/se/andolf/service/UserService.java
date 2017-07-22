package se.andolf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.andolf.api.AccountInfo;
import se.andolf.api.ContactInfo;
import se.andolf.api.User;
import se.andolf.entities.AccountInfoEntity;
import se.andolf.entities.ContactInfoEntity;
import se.andolf.entities.UserEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;
import se.andolf.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2017-07-16.
 */
@Service
public class UserService {

    private final static Log LOG = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public long save(User user) {
        final UserEntity userEntity = toUserEntity(user);

        try {
            return userRepository.save(userEntity).getId();
        } catch (ClientException e) {
            LOG.error(e);
            throw new NodeExistsException("Email " + user.getAccountInfo().getEmail() + " exists please select another email");
        }
    }

    private UserEntity toUserEntity(User user) {
        return new UserEntity.Builder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .addAccountInfo(toAccountInfoEntity(user.getAccountInfo()))
                .addContactInfo(toContactInfoEntity(user.getContactInfo()))
                .build();
    }

    private ContactInfoEntity toContactInfoEntity(ContactInfo contactInfo) {
        return new ContactInfoEntity.Builder().setAddressLine1(contactInfo.getAddressLine1()).build();
    }

    private AccountInfoEntity toAccountInfoEntity(AccountInfo accountInfo) {
        return new AccountInfoEntity.Builder().setEmail(accountInfo.getEmail()).build();
    }

    public void delete(long id) {
        userRepository.deleteUserById(id);
    }

    public List<User> getAll() {
        return userRepository.findAllUsersAsList().stream().map(this::toUser).collect(Collectors.toList());
    }

    private User toUser(UserEntity userEntity) {
        return new User.Builder()
                .setId(userEntity.getId())
                .setFirstname(userEntity.getFirstname())
                .setLastname(userEntity.getLastname())
                .setContactInfo(toContactInfo(userEntity.getContactInfoEntities()))
                .build();
    }

    private ContactInfo toContactInfo(Set<ContactInfoEntity> contactInfoEntities) {
        return contactInfoEntities.stream().findFirst().map(contactInfoEntity -> new ContactInfo.Builder().setAddressLine1(contactInfoEntity.getAddressLine1()).build()).orElse(null);
    }

    public User find(Long id) {
        final Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findById(id));
        if(userEntity.isPresent())
            return toUser(userEntity.get());
        throw new NodeNotFoundException(String.format("Couldn't not find user with id %d", id));
    }
}
