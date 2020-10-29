package com.zabozhan.restApp.order;

public class OrderNotFoundException extends RuntimeException{
    OrderNotFoundException(Long id) {
        super("Could not find item with id : " + id);
    }
}
