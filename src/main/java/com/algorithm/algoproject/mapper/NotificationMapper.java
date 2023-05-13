package com.algorithm.algoproject.mapper;


import com.algorithm.algoproject.dto.NotificationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insertNotification(NotificationDTO notificationDTO);

    List<NotificationDTO> selectNotificationBoard(int offset);
    List<NotificationDTO> selectNotificationBoardBySearch(@Param("keyword") String keyword, @Param("offset") int offset);
    NotificationDTO selectNotificationBoardByPageNum(int pageNum);

    int countAllNotifications();


}
