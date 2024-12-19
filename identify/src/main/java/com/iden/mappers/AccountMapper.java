package com.iden.mappers;

import com.iden.DTO.AccountDTO;
import com.iden.DTO.RegistryInputDTO;
import com.iden.DTO.UserDTO;
import com.iden.models.Account;
import com.iden.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    @Mapping(source = "regis.username", target = "username")
    @Mapping(source = "regis.password", target = "password")
//    @Mapping(source = "regis.phone", target = "phone")
    @Mapping(source = "regis.email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(target = "id", ignore = true) // Bỏ qua ánh xạ cho id
    Account registryInputToAccount(RegistryInputDTO regis, Role role);




    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.role", target = "role")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "account.id", target = "id")
    AccountDTO accountToAccountDTO(Account account, UserDTO user);
}
