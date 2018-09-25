package com.nationalchip.iot.data.model.auth;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.Device;
import com.nationalchip.iot.security.authority.SecurityConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * 消费者
 */

@Entity
@Table(name = "consumer")
public class Consumer extends User implements IConsumer{

    public Consumer(){

    }

    public Consumer(String username,String password){
        super(username,password);
    }



    @Column(name="location")
    @Comment("注册时的地点")
    private String location;


    @Comment("关联的设备")
    @OneToMany(mappedBy = "consumer",targetEntity = Device.class)
    private Set<Device> devices;

    @Override
    public boolean isMatch(String client) {
        return SecurityConstant.CLIENT_APP.equalsIgnoreCase(client);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
}
