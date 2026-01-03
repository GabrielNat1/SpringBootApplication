//package com.example.spring_boot_project.service;
//
//import com.example.spring_boot_project.model.ChatRoom;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class ChatRoomManager {
//    private Map<String, ChatRoom> rooms = new ConcurrentHashMap<>();
//
//    public ChatRoom createRoom(String clientId, String attendantID){
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setId(UUID.randomUUID().toString());
//        chatRoom.setClientId(clientId);
//        chatRoom.setAttendantId(attendantID);
//        chatRoom.setActive(true);
//
//        chatRoom.put(chatRoom.getId(), chatRoom);
//        return chatRoom;
//    }
//
//    public Optional<ChatRoom> getRoomByUser(String userId) {
//        return rooms.values().stream()
//                .filter(r -> r.getClientId().equals(userId) || r.getAttendantId().equals(userId))
//                .findFirst();
//    }
//
//}
