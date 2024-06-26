package com.example.demo.controllers;

import com.example.demo.models.Delivery;
import com.example.demo.models.DeliveryDTO;
import com.example.demo.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("deliveries")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping
    private ResponseEntity<List<Delivery>> getAllDeliveries(){
        return new ResponseEntity<>(deliveryService.getAllDeliveries(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    private ResponseEntity<Delivery> getDeliveryById(@PathVariable long id){
        Optional<Delivery> delivery = deliveryService.getDeliveryById(id);
        if(delivery.isPresent()){
            return new ResponseEntity<>(delivery.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/{id}")
    private ResponseEntity<Delivery> setDelivered(@PathVariable long id){
        return new ResponseEntity<>(deliveryService.completeDelivery(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Delivery> addNewDelivery(@RequestBody DeliveryDTO newDeliveryDTO) {
        Delivery newDelivery = deliveryService.saveDelivery(newDeliveryDTO);
        return new ResponseEntity<>(newDelivery, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteDelivery(@PathVariable long id){
        Optional<Delivery> delivery = deliveryService.findDelivery(id);
        if(delivery.isPresent()){
            deliveryService.deleteDelivery(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
