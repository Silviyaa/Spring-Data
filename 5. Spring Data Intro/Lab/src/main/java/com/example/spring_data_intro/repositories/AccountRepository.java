package com.example.spring_data_intro.repositories;

import com.example.spring_data_intro.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findAccountById(Long id);
}
