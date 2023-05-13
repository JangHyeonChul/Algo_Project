package com.algorithm.algoproject;


import org.junit.jupiter.api.Test;

public class PageHandlerTest {

    @Test
    public void pageHandlerTest() {
        PageHandler ph = new PageHandler(251, 10);

        ph.print();
        System.out.println("ph = " + ph);


    }

}
