package com.trzewik.jdbc.domain

class AccountRepositoryInMemory implements AccountRepository, AccountCreation {
    final Map<Long, Account> repository = new HashMap<>()

    @Override
    synchronized List<Account> getAll() {
        return new ArrayList<>(repository.values())
    }

    @Override
    synchronized Optional<Account> findById(long userId) {
        Account account = repository.get(userId)
        return Optional.ofNullable(account)
    }

    @Override
    synchronized void save(Account account) {
        repository.put(account.getUserId(), createAccount(new AccountCreator(account)))
    }

    @Override
    synchronized void saveMany(List<Account> accounts) {
        accounts.each { a -> repository.put(a.getUserId(), createAccount(new AccountCreator(a))) }
    }

    @Override
    synchronized void delete(Account account) {
        repository.remove(account.getUserId())
    }

    @Override
    synchronized void update(Account account) {
        repository.put(account.getUserId(), createAccount(new AccountCreator(account)))
    }
}
