package net.zero.cloud.auth.mapper;

import net.zero.cloud.domain.BaseElement;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

public interface BaseElementMapper extends Mapper<BaseElement> {
    public List<BaseElement> selectAuthorityBaseElementByUserId(@Param("userId") String userId);
    public List<BaseElement> selectAuthorityMenuBaseElementByUserId(@Param("userId") String userId, @Param("menuId") String menuId);
    public List<BaseElement> selectAuthorityBaseElementByClientId(@Param("clientId") String clientId);
    public List<BaseElement> selectAllBaseElementPermissions();
	public List<BaseElement> selectAuthorityBaseElementByRoleId(@Param("roleId") int roleId);
	public List<BaseElement> selectAuthorityBaseElementByRoleIdAndMenuId(@Param("roleId") int roleId, @Param("menuId") int menuId);
	public Map<String, Object> selectByDeptId(Example example);
}