package com.nationalchip.iot.data.configuration;

import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

public class JpaVendorAdapter extends EclipseLinkJpaVendorAdapter {
    private final JpaDialect dialect = new JpaDialect();
    @Override
    public EclipseLinkJpaDialect getJpaDialect(){
        return dialect;
    }
}