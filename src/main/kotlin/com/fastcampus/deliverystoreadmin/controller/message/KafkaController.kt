package com.fastcampus.deliverystoreadmin.controller.message

import com.fastcampus.deliverystoreadmin.service.message.KafkaProduceService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class KafkaController(
    private val kafkaProduceService: KafkaProduceService
) {

    companion object {
        private const val TOPIC = "delivery-payment-complete"
    }

    @PostMapping("/kafka/publish")
    fun sendMessage(@RequestBody message: String) {
        kafkaProduceService.sendMessage(TOPIC, message)
    }
}