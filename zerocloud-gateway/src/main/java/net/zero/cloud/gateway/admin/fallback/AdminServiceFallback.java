package net.zero.cloud.gateway.admin.fallback;

import lombok.extern.slf4j.Slf4j;
import net.zero.cloud.common.permission.PermissionInfo;
import net.zero.cloud.common.web.ResponseDto;
import net.zero.cloud.gateway.admin.AdminService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Slf4j
public class AdminServiceFallback implements AdminService {
    @Override
    public ResponseDto<String> getPermissionByUsername(@RequestParam("uri") String uri) {
        log.error("调用{}异常{}","getPermissionByUsername",uri);
        return null;
    }

    @Override
    public List<String> getAccessBean(@RequestParam("uri") String uri) {
        log.error("调用{}异常","getPermissionByUsername");
        return null;
    }
}
