package com.trzewik.jdbc.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode
@ToString
@Getter
class AccountImpl implements Account {
    private final long userId;
    private final @NonNull String username;
    private final @NonNull String email;

    AccountImpl(long userId, String username, String email) {
        if (StringUtils.isAnyBlank(username, email)) {
            throw new Account.CreationException(username, email);
        }
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
