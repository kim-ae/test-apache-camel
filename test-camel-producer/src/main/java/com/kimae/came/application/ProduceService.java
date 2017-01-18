package com.kimae.came.application;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProduceService {
    
    @Autowired
    private Producer producer;

    @RequestMapping("/produce/{number}")
    public String produce(@PathVariable Integer number){
        IntStream.range(0, number)
                .forEach(i -> producer.produceMessage(i));
        return number.toString() + " produced";
    }
}
