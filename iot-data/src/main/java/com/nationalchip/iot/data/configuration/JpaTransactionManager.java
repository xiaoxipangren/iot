package com.nationalchip.iot.data.configuration;

import com.nationalchip.iot.tenancy.ITenantAware;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 2:17 PM
 * @Modified:
 */

public class JpaTransactionManager extends org.springframework.orm.jpa.JpaTransactionManager {

    @Autowired
    private transient ITenantAware tenantAware;

    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition) {
        super.doBegin(transaction, definition);

        final String currentTenant = tenantAware.getCurrentTenant();
        if (currentTenant != null) {
            final EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
                    .getResource(getEntityManagerFactory());
            final EntityManager em = emHolder.getEntityManager();
            em.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, currentTenant.toUpperCase());
        }
    }

}
