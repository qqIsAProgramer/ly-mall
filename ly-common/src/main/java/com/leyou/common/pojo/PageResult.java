package com.leyou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装类
 * @Author: qyl
 * @Date: 2021/1/24 16:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private Long total;  // 总条数

    private Integer totalPage;  // 总页数

    private List<T> items;  // 当前页数据

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
