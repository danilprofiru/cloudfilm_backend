package com.cloudfilmtech.cloudfilm.service;

public interface EmailService {
    /**
     * Отправка email сообщения.
     *
     * @param to      адрес получателя
     * @param subject тема письма
     * @param content содержимое письма
     */
    void sendEmail(String to, String subject, String content);
}
