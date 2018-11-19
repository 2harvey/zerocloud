package net.zero.cloud.common.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "base_platform")
public class BasePlatform {
    /**
     * 平台id
     */
    @Id @Column(name = "platform_id")
    private Long platformId;

    /**
     * 平台key
     */
    @Column(name = "platform_key")
    private String platformKey;

    /**
     * 平台名称
     */
    @Column(name = "platform_name")
    private String platformName;

    /**
     * 平台简介
     */
    @Column(name = "platform_intro")
    private String platformIntro;

}