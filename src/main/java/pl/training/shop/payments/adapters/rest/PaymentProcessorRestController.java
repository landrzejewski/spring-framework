package pl.training.shop.payments.adapters.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/payments")
@RestController
public class PaymentProcessorRestController {

    @GetMapping
    public String sayHello() {
        return "Hello World!";
    }

}
