package com.ws.http;

import com.ws.http.dto.QueryUserCommand;
import com.ws.http.dto.UserDto;

/**
 * create by gl
 * on 2018/5/2
 */
public interface LocalHttpRequest {

    @HttpInvoker(schema = "http", host = "127.0.0.1", port = 9000, path = "/users")
    UserDto queryUsers(QueryUserCommand command);
}
