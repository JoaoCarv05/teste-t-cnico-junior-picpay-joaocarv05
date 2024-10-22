package com.joaocarv05.teste_tecnico_picpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.joaocarv05.teste_tecnico_picpay.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
