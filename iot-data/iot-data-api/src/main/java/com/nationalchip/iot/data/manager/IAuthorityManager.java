package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.auth.IAuthority;
import com.nationalchip.iot.data.model.auth.Operation;
import com.nationalchip.iot.security.authority.AuthorityExpression;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/18/18 1:03 PM
 * @Modified:
 */
public interface IAuthorityManager extends IManager<IAuthority> {
    @PreAuthorize(AuthorityExpression.HAS_ROLE_SYSTEM)
    IAuthority create(IAuthority authority);

    @PreAuthorize(AuthorityExpression.HAS_ROLE_SYSTEM)
    IAuthority update(IAuthority authority);

    boolean exists(Operation operation,String target);


    IAuthority findByOperationAndTarget(Operation operation,String target);


    @PreAuthorize(AuthorityExpression.HAS_ROLE_SYSTEM)
    void deleteByOperationAndTarget(Operation operation,String target);
}
