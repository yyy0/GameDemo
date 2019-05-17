package com.client.facade;

import com.client.dispatcher.ClientHandlerAnno;
import com.server.map.packet.SM_AccountMove;
import com.server.map.packet.SM_ChangeMap;
import com.server.user.account.packet.SM_AccountInfo;
import com.server.user.account.packet.SM_LoginSuccess;
import com.server.user.account.packet.SM_RegSuccess;
import org.springframework.stereotype.Component;


/**
 * @author yuxianming
 * @date 2019/5/17 15:51
 */
@Component
public class ClientFacade {

    @ClientHandlerAnno
    public void clientLoginSuccess(SM_LoginSuccess packet) {
        System.out.println("账号：【" + packet.getAccountId() + "】登陆成功,当前地图:" + packet.getMapId());
    }

    @ClientHandlerAnno
    public void clientRegSuccess(SM_RegSuccess packet) {
        System.out.println("账号：【" + packet.getAccountId() + "】注册成功,账号名称：" + packet.getName());
    }

    @ClientHandlerAnno
    public void clientPrintAccount(SM_AccountInfo packet) {
        System.out.println(packet);
    }

    @ClientHandlerAnno
    public void clientChangeMap(SM_ChangeMap packet) {
        System.out.println(packet);
    }

    @ClientHandlerAnno
    public void clientMove(SM_AccountMove packet) {
        System.out.println(packet);
    }

}
