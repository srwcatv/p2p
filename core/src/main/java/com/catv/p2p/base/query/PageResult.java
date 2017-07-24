package com.catv.p2p.base.query;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

//分页查询结果对象
@SuppressWarnings("all")
@Getter
public class PageResult {
    private List result;// 当前页的结果集数据:查询
    private Integer totalCount;// 总数据条数:查询

    private Integer currentPage = 1;
    private Integer pageSize = 10;

    private Integer prevPage;// 上一页
    private Integer nextPage;// 下一页
    private Integer totalPage;// 总页数

    // 如果总数据条数为0,返回一个空集
    public static PageResult empty(Integer pageSize) {
        return new PageResult(new ArrayList<>(), 0, 1, pageSize);
    }

    public int getTotalPage() {
        return totalPage == 0 ? 1 : totalPage;
    }

    public PageResult(List result, Integer totalCount, Integer currentPage,
                      Integer pageSize) {
        this.result = result;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        // ----------------------------------------
        this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount
                / this.pageSize : this.totalCount / this.pageSize + 1;

        this.prevPage = this.currentPage - 1 >= 1 ? this.currentPage - 1 : 1;
        this.nextPage = this.currentPage + 1 <= this.totalPage ? this.currentPage + 1
                : this.totalPage;
    }
}
