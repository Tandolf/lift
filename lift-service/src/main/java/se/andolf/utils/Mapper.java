package se.andolf.utils;

import se.andolf.api.AccountInfo;
import se.andolf.api.ContactInfo;
import se.andolf.api.User;
import se.andolf.entities.AccountInfoEntity;
import se.andolf.entities.ContactInfoEntity;
import se.andolf.entities.UserEntity;

import java.util.Set;

/**
 * @author Thomas on 2017-07-29.
 */
public final class Mapper {

    private Mapper() {
    }

    public static User toUser(UserEntity userEntity) {
        return new User.Builder()
                .setId(userEntity.getId())
                .setFirstname(userEntity.getFirstname())
                .setLastname(userEntity.getLastname())
                .setAccountInfo(toAccountInfo(userEntity.getAccountInfoEntities()))
                .setContactInfo(toContactInfo(userEntity.getContactInfoEntities()))
                .build();
    }

    public static ContactInfo toContactInfo(Set<ContactInfoEntity> contactInfoEntities) {
        return contactInfoEntities.stream().findFirst().map(Mapper::toContactInfo).orElse(null);
    }

    public static AccountInfo toAccountInfo(Set<AccountInfoEntity> accountInfoEntities) {
        return accountInfoEntities.stream().findFirst().map(Mapper::toAccountInfo).orElse(null);
    }

    public static AccountInfo toAccountInfo(AccountInfoEntity accountInfoEntity) {
        return new AccountInfo.Builder().setEmail(accountInfoEntity.getEmail()).build();
    }

    public static ContactInfo toContactInfo(ContactInfoEntity contactInfoEntity) {
        return new ContactInfo.Builder().
                setAddressLine1(contactInfoEntity.getAddressLine1())
                .setAddressLine2(contactInfoEntity.getAddressLine2())
                .setCity(contactInfoEntity.getCity())
                .setPostalCode(contactInfoEntity.getPostalCode())
                .setStateOrProvince(contactInfoEntity.getStateOrProvince())
                .setMobileNumber(contactInfoEntity.getMobileNumber())
                .build();
    }


    public static UserEntity toUserEntity(User user) {
        return new UserEntity.Builder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .addAccountInfo(toAccountInfoEntity(user.getAccountInfo()))
                .addContactInfo(toContactInfoEntity(user.getContactInfo()))
                .build();
    }

    public static ContactInfoEntity toContactInfoEntity(ContactInfo contactInfo) {
        return new ContactInfoEntity.Builder()
                .setAddressLine1(contactInfo.getAddressLine1())
                .setAddressLine2(contactInfo.getAddressLine2())
                .setCity(contactInfo.getCity())
                .setStateOrProvince(contactInfo.getStateOrProvince())
                .setPostalCode(contactInfo.getPostalCode())
                .setMobileNumber(contactInfo.getMobileNumber()).build();
    }

    public static AccountInfoEntity toAccountInfoEntity(AccountInfo accountInfo) {
        return new AccountInfoEntity.Builder().setEmail(accountInfo.getEmail()).build();
    }
}
