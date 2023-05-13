package com.algorithm.algoproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {

    private int noti_no;
    private String user_id;
    private String noti_title;
    private String noti_content;
    private LocalDateTime noti_create;

    private String transNotiTime;

    NotificationDTO() {}
}
