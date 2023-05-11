package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.webshop.model.AppUser;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserById(Long id);
    Optional<AppUser> findAppUserByUsername(String username);

    void deleteAppUserById(Long id);

    Optional<AppUser> findAppUserByMail(String mail);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.mail = ?1")
    int enableAppUser(String mail);

}
