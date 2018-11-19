package net.zero.cloud.common.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "base_dept")
public class BaseDept {
    @Id
    private Integer id;

    /**
     * 所属平台自增ID
     */
    @Column(name = "platform_id")
    private Long platformId;

    /**
     * 上级部门ID，一级部门为0
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    @Column(name = "del_flag")
    private Byte delFlag;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取所属平台自增ID
     *
     * @return platform_id - 所属平台自增ID
     */
    public Long getPlatformId() {
        return platformId;
    }

    /**
     * 设置所属平台自增ID
     *
     * @param platformId 所属平台自增ID
     */
    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    /**
     * 获取上级部门ID，一级部门为0
     *
     * @return parent_id - 上级部门ID，一级部门为0
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级部门ID，一级部门为0
     *
     * @param parentId 上级部门ID，一级部门为0
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取排序
     *
     * @return order_num - 排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置排序
     *
     * @param orderNum 排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取是否删除  -1：已删除  0：正常
     *
     * @return del_flag - 是否删除  -1：已删除  0：正常
     */
    public Byte getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除  -1：已删除  0：正常
     *
     * @param delFlag 是否删除  -1：已删除  0：正常
     */
    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }
}