package com.zabozhan.restApp.items;

public class ItemNotFoundException extends RuntimeException {
    ItemNotFoundException(Long id){
        super("Could not find item with id : " + id);
    }
}
