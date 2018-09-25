package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.auth.Authority;
import com.nationalchip.iot.data.model.auth.IAuthority;
import com.nationalchip.iot.data.model.auth.Operation;
import com.nationalchip.iot.data.repository.AuthorityRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/18/18 1:08 PM
 * @Modified:
 */
@Component
public class AuthorityManager extends BaseManager<IAuthority, Authority> implements IAuthorityManager {

    @Override
    public boolean exists(Operation operation, String target) {
        return getRepository().existsByOperationAndTarget(operation, target);
    }

    @Override
    public AuthorityRepository getRepository() {
        return (AuthorityRepository) super.getRepository();
    }

    @Override
    public IAuthority findByOperationAndTarget(Operation operation, String target) {
        return getRepository().findByOperationAndTarget(operation, target);
    }

    @Override
    public void deleteByOperationAndTarget(Operation operation, String target) {
        getRepository().deleteByOperationAndTarget(operation, target);
    }

    @Override
    public IAuthority create(IAuthority authority) {
        return super.create(authority);
    }
}
