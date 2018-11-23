package net.zero.cloud.auth.controller;

import lombok.extern.slf4j.Slf4j;
import net.zero.cloud.common.web.ResponseCode;
import net.zero.cloud.common.web.ResponseDto;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @RequestMapping(value = "/strategy")
    public ResponseDto<String> getStrategyByUri(@RequestParam("uri") String uri){
        log.debug("根据URI查询策略:{}",uri);
        String strategys  = "checkLogin,checkPayPassword";
        return new ResponseDto<>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDescription(),strategys);
    }

    @RequestMapping(value="/access")
    public List<String> access(@RequestParam("uri") String uri){
        String[] ls = new String[]{"a","b","钱包"};
        return CollectionUtils.arrayToList(ls);
    }
}
