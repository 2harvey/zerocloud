package net.zero.cloud.auth.mapper;

import net.zero.cloud.common.domain.AdminUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<AdminUser> {
	public int addUser(AdminUser entity);
}