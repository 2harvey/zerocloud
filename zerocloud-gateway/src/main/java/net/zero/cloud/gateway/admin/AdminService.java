package net.zero.cloud.gateway.admin;

import net.zero.cloud.common.permission.PermissionInfo;
import net.zero.cloud.common.web.ResponseDto;
import net.zero.cloud.gateway.admin.fallback.AdminServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "zerocloud-auth",fallback = AdminServiceFallback.class)
public interface AdminService {

    @RequestMapping(value="/auth/strategy",method = RequestMethod.GET)
    ResponseDto<String> getPermissionByUsername(@RequestParam("uri") String uri);

    @RequestMapping(value="/auth/access",method = RequestMethod.GET)
    List<String> getAccessBean(@RequestParam("uri") String uri);

}
