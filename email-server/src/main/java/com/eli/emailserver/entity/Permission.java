package com.eli.emailserver.entity;

import java.io.Serializable;

/**
 * sys_permission
 * @author 
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = 6351794376845840762L;
    /**
     * 自定id,主要供前端展示权限列表分类排序使用.
     */
    private Integer id;

    /**
     * 归属菜单,前端判断并展示菜单使用,
     */
    private String menuCode;

    /**
     * 菜单的中文释义
     */
    private String menuName;

    /**
     * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     */
    private String permissionCode;

    /**
     * 本权限的中文释义
     */
    private String permissionName;

    /**
     * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
     */
    private Boolean requiredPermission;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Boolean getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(Boolean requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Permission other = (Permission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMenuCode() == null ? other.getMenuCode() == null : this.getMenuCode().equals(other.getMenuCode()))
            && (this.getMenuName() == null ? other.getMenuName() == null : this.getMenuName().equals(other.getMenuName()))
            && (this.getPermissionCode() == null ? other.getPermissionCode() == null : this.getPermissionCode().equals(other.getPermissionCode()))
            && (this.getPermissionName() == null ? other.getPermissionName() == null : this.getPermissionName().equals(other.getPermissionName()))
            && (this.getRequiredPermission() == null ? other.getRequiredPermission() == null : this.getRequiredPermission().equals(other.getRequiredPermission()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMenuCode() == null) ? 0 : getMenuCode().hashCode());
        result = prime * result + ((getMenuName() == null) ? 0 : getMenuName().hashCode());
        result = prime * result + ((getPermissionCode() == null) ? 0 : getPermissionCode().hashCode());
        result = prime * result + ((getPermissionName() == null) ? 0 : getPermissionName().hashCode());
        result = prime * result + ((getRequiredPermission() == null) ? 0 : getRequiredPermission().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", menuCode=").append(menuCode);
        sb.append(", menuName=").append(menuName);
        sb.append(", permissionCode=").append(permissionCode);
        sb.append(", permissionName=").append(permissionName);
        sb.append(", requiredPermission=").append(requiredPermission);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}