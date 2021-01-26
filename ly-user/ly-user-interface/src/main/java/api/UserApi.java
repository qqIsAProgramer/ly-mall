package api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: qyl
 * @Date: 2021/1/23 22:09
 */
public interface UserApi {

    @GetMapping("/query")
    User queryUser(@RequestParam("username") String username,
                   @RequestParam("password") String password);
}
