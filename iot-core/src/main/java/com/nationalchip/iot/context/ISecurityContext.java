package com.nationalchip.iot.context;

import java.util.concurrent.Callable;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/22/18 12:05 PM
 * @Modified:
 */
public interface ISecurityContext {
    <T> T runAsSystem(final Callable<T> callable);


    <T> T runAsSystemAsTenant(final Callable<T> callable, final String tenant);

}
