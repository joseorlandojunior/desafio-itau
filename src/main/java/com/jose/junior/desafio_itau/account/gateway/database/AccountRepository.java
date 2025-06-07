package com.jose.junior.desafio_itau.account.gateway.database;

import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDatabase, Long> {
}
