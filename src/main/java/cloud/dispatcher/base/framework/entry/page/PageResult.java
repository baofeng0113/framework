package cloud.dispatcher.base.framework.entry.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public static final PageResult EMPTY = new PageResult<>(0, Collections.emptyList(), 0, 0);

    public static PageResult empty() { return EMPTY; }

    @Getter @Setter private long total; //记录总数

    @Getter @Setter private List<T> items; //数据列表

    @Getter @Setter private int page; //当前页码

    @Getter @Setter private int size; //每页数量
}
