package com.example.spring_boot_project.service;

import com.example.spring_boot_project.dto.MatchDTO;
//import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class QueueService {
    private Queue<String> waitingClients = new ConcurrentLinkedQueue<>();
    private Queue<String> availableAttendants = new ConcurrentLinkedQueue<>();

    public void addClient(String ClientId){
        waitingClients.add(ClientId);
    }

    public void addAttendants(String AttendantId){
        availableAttendants.add(AttendantId);
    }

    public synchronized Optional<MatchDTO> match() {
        if (!waitingClients.isEmpty() && !availableAttendants.isEmpty()) {
            return Optional.of(
                    new MatchDTO(
                            waitingClients.poll(),
                            availableAttendants.poll()
                    )
            );
        }
        return Optional.empty();
    }
}
