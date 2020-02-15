package com.trzewik.jdbc.db;

import com.trzewik.jdbc.domain.Account;
import com.trzewik.jdbc.domain.AccountFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDto {
    private long userId;
    private String username;
    private String email;

    public static AccountDto from(Account account) {
        return new AccountDto(
            account.getUserId(),
            account.getUsername(),
            account.getEmail()
        );
    }

    public static AccountDto from(AccountEntity entity) {
        return new AccountDto(
            entity.getUserId(),
            entity.getUsername(),
            entity.getEmail()
        );
    }

    public static Account to(AccountDto dto) {
        return AccountFactory.create(
            dto.getUserId(),
            dto.getUsername(),
            dto.getEmail()
        );
    }

    public static AccountEntity toEntity(AccountDto dto) {
        return new AccountEntity(
            dto.getUserId(),
            dto.getUsername(),
            dto.getEmail()
        );
    }
}
