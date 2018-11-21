package net.zero.cloud.auth.mapper;

import net.zero.cloud.common.domain.BaseRole;
import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<BaseRole> {
	public Integer insertRole(BaseRole role);
}