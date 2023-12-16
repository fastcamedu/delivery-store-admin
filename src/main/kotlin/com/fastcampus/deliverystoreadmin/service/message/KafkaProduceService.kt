package com.fastcampus.deliverystoreadmin.service.message

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProduceService(private val kafkaTemplate: KafkaTemplate<String, String>) {

    companion object {
        val logger = KotlinLogging.logger(this::class.java.name)
    }

    fun sendMessage(topic: String, message: String) {
        logger.info { ">>> Payment Message Produce: $topic, $message" }
        kafkaTemplate.send(topic, message)
    }
}