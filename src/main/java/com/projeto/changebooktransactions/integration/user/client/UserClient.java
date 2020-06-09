package com.projeto.changebooktransactions.integration.user.client;

import com.projeto.changebooktransactions.integration.user.response.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user", url = "${user.url}")
public interface UserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/token/valid")
    String validateToken(@RequestHeader("Authorization") String token);

    @RequestMapping(method = RequestMethod.GET, value = "/user/by_token")
    User getUserByToken(@RequestHeader("Authorization") String token);
}
