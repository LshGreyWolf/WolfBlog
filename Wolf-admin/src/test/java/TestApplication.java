import com.alibaba.fastjson.JSON;
import com.lsh.adminApplication;
import com.lsh.domain.entity.Menu;
import com.lsh.mapper.MenuMapper;
import com.lsh.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = adminApplication.class)
@Slf4j
public class TestApplication {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void te() {
//        List<String> strings = menuMapper.selectPermsByUserId(1L);
        List<Menu> strings = menuMapper.listRouters();
        log.info("jiegou:{}", JSON.toJSONString(strings));
    }


}
