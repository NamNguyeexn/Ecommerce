package com.iden.mappers;

import com.iden.DTO.AccountDTO;
import com.iden.DTO.AccountDTO.AccountDTOBuilder;
import com.iden.DTO.RegistryInputDTO;
import com.iden.DTO.RoleDTO;
import com.iden.DTO.RoleDTO.RoleDTOBuilder;
import com.iden.DTO.UserDTO;
import com.iden.models.Account;
import com.iden.models.Account.AccountBuilder;
import com.iden.models.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-17T22:47:43+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account registryInputToAccount(RegistryInputDTO regis, Role role) {
        if ( regis == null && role == null ) {
            return null;
        }

        AccountBuilder account = Account.builder();

        if ( regis != null ) {
            account.username( regis.getUsername() );
            account.password( regis.getPassword() );
            account.email( regis.getEmail() );
        }
        if ( role != null ) {
            account.role( role );
            account.created_at( role.getCreated_at() );
            account.updated_at( role.getUpdated_at() );
        }

        return account.build();
    }

    @Override
    public AccountDTO accountToAccountDTO(Account account, UserDTO user) {
        if ( account == null && user == null ) {
            return null;
        }

        AccountDTOBuilder accountDTO = AccountDTO.builder();

        if ( account != null ) {
            accountDTO.username( account.getUsername() );
            accountDTO.email( account.getEmail() );
            accountDTO.role( roleToRoleDTO( account.getRole() ) );
            accountDTO.id( account.getId() );
        }
        if ( user != null ) {
            accountDTO.user( user );
        }

        return accountDTO.build();
    }

    protected RoleDTO roleToRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTOBuilder roleDTO = RoleDTO.builder();

        roleDTO.name( role.getName() );

        return roleDTO.build();
    }
}
