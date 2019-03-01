package com.nilangpatel.springauth.repository;


import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import com.nilangpatel.springauth.model.LdapAuthUser;

@Repository
public interface LdapAuthRepository extends LdapRepository<LdapAuthUser>,LdapAuthRepositoryCustom{

}
