package net.zero.cloud.common.token;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TokenInfo implements Serializable {
	
	/**
	 * 登陆用户名称
	 */
    private String name;
    /**
     * 用户id userId
     */
    private String XId;
    /**
     * 平台编号
     * */
    private String platformId;
    /**
     * 签名盐值
     * */
    private String saltKey;

}
