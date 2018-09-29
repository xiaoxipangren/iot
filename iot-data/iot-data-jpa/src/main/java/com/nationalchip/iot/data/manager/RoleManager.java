package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.auth.IRole;
import com.nationalchip.iot.data.model.auth.Role;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/18/18 1:07 PM
 * @Modified:
 */

@Component
public class RoleManager extends NamedManager<IRole,Role> implements IRoleManager {

}
