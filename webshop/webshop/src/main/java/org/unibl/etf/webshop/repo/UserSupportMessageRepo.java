package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.webshop.model.UserSupportMessage;

public interface UserSupportMessageRepo extends JpaRepository<UserSupportMessage, Long> {
}
