package com.trzewik.jdbc.db;

import com.trzewik.jdbc.domain.Account;
import com.trzewik.jdbc.domain.AccountRepository;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
class AccountRepositoryImpl implements AccountRepository {
    private final Dao<AccountEntity> dao;

    @Override
    public List<Account> getAll() {
        try {
            return dao.findAll().stream().map(AccountDto::from).map(AccountDto::to).collect(Collectors.toList());
        } catch (SQLException ex) {
            throw new AccountRepository.Exception(ex);
        }
    }

    @Override
    public Optional<Account> findById(long userId) {
        try {
            return dao.findById(userId).map(AccountDto::from).map(AccountDto::to);
        } catch (SQLException ex) {
            throw new AccountRepository.Exception(ex);
        }
    }

    @Override
    public void save(Account account) {
        try {
            dao.save(AccountDto.toEntity(AccountDto.from(account)));
        } catch (SQLException ex) {
            throw new AccountRepository.Exception(ex);
        }

    }

    @Override
    public void saveMany(List<Account> accounts) {
        try {
            dao.saveMany(accounts.stream()
                .map(AccountDto::from)
                .map(AccountDto::toEntity)
                .collect(Collectors.toList())
            );
        } catch (SQLException ex) {
            throw new AccountRepository.Exception(ex);
        }
    }

    @Override
    public void delete(Account account) {
        try {
            dao.delete(AccountDto.toEntity(AccountDto.from(account)));
        } catch (SQLException ex) {
            throw new AccountRepository.Exception(ex);
        }
    }

    @Override
    public void update(Account account) {
        try {
            dao.update(AccountDto.toEntity(AccountDto.from(account)));
        } catch (SQLException ex) {
            throw new AccountRepository.Exception(ex);
        }
    }
}
