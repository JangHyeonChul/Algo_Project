package com.algorithm.algoproject;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageHandler {
    int totalCnt;
    int pageSize = 20;
    int naviSize = 10;
    int totalPage;
    int page;
    int beginPage;
    int endPage;
    boolean showPrev;
    boolean showNext;

    public PageHandler(int totalCnt, int page) {
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;

        totalPage = (int)Math.ceil(totalCnt / (double)pageSize);

        beginPage = (page-1) / naviSize * naviSize + 1;
        endPage = Math.min(beginPage + naviSize-1, totalPage);

        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }


    void print() {
        System.out.println("page = " + page);
        System.out.println(showPrev ? "[PREV] " : "");
        for(int i = beginPage; i<=endPage; i++) {
            System.out.println(i + " ");
        }
        System.out.println(showNext ? "[NEXT]" : "");
    }

}
