package com.alibaba.dm.vo;

import java.io.Serializable;
import java.util.List;

public class ItemTypeResponse implements Serializable {
    private Long id;
    private String itemType;
    private Long level;
    private Long parent;
    private String aliasName;
    private List<ItemTypeResponse> children;
    private List<ItemResponse> hotItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public List<ItemTypeResponse> getChildren() {
        return children;
    }

    public void setChildren(List<ItemTypeResponse> children) {
        this.children = children;
    }

    public List<ItemResponse> getHotItems() {
        return hotItems;
    }

    public void setHotItems(List<ItemResponse> hotItems) {
        this.hotItems = hotItems;
    }
}
