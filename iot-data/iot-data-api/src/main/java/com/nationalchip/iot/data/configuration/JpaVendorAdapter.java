package com.nationalchip.iot.data.configuration;

import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

public class JpaVendorAdapter extends EclipseLinkJpaVendorAdapter {

    public JpaVendorAdapter(boolean showSql){
        this.setShowSql(showSql);
    }


    private final JpaDialect dialect = new JpaDialect();
    @Override
    public EclipseLinkJpaDialect getJpaDialect(){
        return dialect;
    }
}