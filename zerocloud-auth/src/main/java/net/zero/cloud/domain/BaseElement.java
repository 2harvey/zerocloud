package net.zero.cloud.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "base_element")
@Data
public class BaseElement {
    @Id
    private Integer id;

    private String code;

    private String type;

    private String name;

    private String strategy;

    private String uri;

    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "parent_id")
    private String parentId;

    private String path;

    private String method;

    private String description;
}