package net.zero.cloud.auth.mapper;

import net.zero.cloud.domain.BaseDept;
import tk.mybatis.mapper.common.Mapper;

public interface DeptMapper extends Mapper<BaseDept> {

	void updateByPlatformId(BaseDept dept);
}