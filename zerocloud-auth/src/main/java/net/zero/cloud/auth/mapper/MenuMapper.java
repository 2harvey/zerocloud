package net.zero.cloud.auth.mapper;

import net.zero.cloud.common.domain.BaseMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<BaseMenu> {
    public List<BaseMenu> selectMenuByAuthorityId(@Param("authorityId") String authorityId, @Param("authorityType") String authorityType);

    /**
     * 根据用户和组的权限关系查找用户可访问菜单
     * @param userId
     * @return
     */
    public List<BaseMenu> selectAuthorityMenuByUserId(@Param("userId") Long userId);

    /**
     * 根据用户和组的权限关系查找用户可访问的系统
     * @param userId
     * @return
     */
    public List<BaseMenu> selectAuthoritySystemByUserId(@Param("userId") int userId);
}