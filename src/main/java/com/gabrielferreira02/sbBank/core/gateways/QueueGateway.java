package com.gabrielferreira02.sbBank.core.gateways;

import com.gabrielferreira02.sbBank.core.dto.SendEmailQueuePayload;

public interface QueueGateway {
    void sendEmailQueue(SendEmailQueuePayload payload);
}
