package net.zero.cloud.common.domain;

import javax.persistence.*;

@Table(name = "base_dict")
public class BaseDict {
    @Id
    private Integer id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 字典码
     */
    private String code;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记  -1：已删除  0：正常
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
     * 获取字典名称
     *
     * @return name - 字典名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字典名称
     *
     * @param name 字典名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取字典类型
     *
     * @return type - 字典类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置字典类型
     *
     * @param type 字典类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取字典码
     *
     * @return code - 字典码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置字典码
     *
     * @param code 字典码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取字典值
     *
     * @return value - 字典值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置字典值
     *
     * @param value 字典值
     */
    public void setValue(String value) {
        this.value = value;
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
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取删除标记  -1：已删除  0：正常
     *
     * @return del_flag - 删除标记  -1：已删除  0：正常
     */
    public Byte getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记  -1：已删除  0：正常
     *
     * @param delFlag 删除标记  -1：已删除  0：正常
     */
    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }
}