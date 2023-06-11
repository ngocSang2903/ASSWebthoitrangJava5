package com.code.AssJava5.service;

import com.code.AssJava5.dto.MailInfo;
import jakarta.mail.MessagingException;

public interface MailerService {

    void send(MailInfo mail) throws MessagingException;

    void queue(MailInfo mail);
}
