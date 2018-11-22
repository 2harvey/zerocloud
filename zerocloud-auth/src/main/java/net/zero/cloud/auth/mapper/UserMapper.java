package net.zero.cloud.auth.mapper;

import net.zero.cloud.domain.AdminUser;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<AdminUser> {
	public int addUser(AdminUser entity);
}