package org.example.services;

import java.io.IOException;
import org.example.dto.MessageDto;

public interface ChatService {

  void send(MessageDto messageDto) throws IOException;
  void startPollingMessages();
  void stopPollingMessages();
}
