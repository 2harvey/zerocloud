package net.zero.cloud.auth.mapper;

import net.zero.cloud.common.domain.BaseUserRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<BaseUserRole> {
	
	List<BaseUserRole> getUserRolesByUserId(Long id);
	
}