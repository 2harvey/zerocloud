package net.zero.cloud.auth.mapper;

import net.zero.cloud.domain.BaseRole;
import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<BaseRole> {
	public Integer insertRole(BaseRole role);
}