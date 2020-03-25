package backend.Service;

import java.util.List;
import java.util.Map;

public interface RmiService {

    // Rmi服务，按照书籍名称查询详情
    List<Map<String, Object>> getDetail(String book);

}

