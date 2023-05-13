package com.algorithm.algoproject.service;


import org.apache.ibatis.annotations.Param;

public interface PointService {


    void pointServiceHandler(String result, int pageNum);
    void updateAddPoint(int point, String username);

}
