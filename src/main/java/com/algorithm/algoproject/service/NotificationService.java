package com.algorithm.algoproject.service;


import com.algorithm.algoproject.PageHandler;
import com.algorithm.algoproject.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    void writeNotification(NotificationDTO notificationDTO);

    List<NotificationDTO> getNotificationBoard(int offset);
    List<NotificationDTO> getNotificationBoardBySearch(String keyword, int offset);
    NotificationDTO getNotificationBoardByPageNum(int pageNum);

    int getNotificationCounts();
    int getNotificationOffset(int page, PageHandler pageHandler);
}
