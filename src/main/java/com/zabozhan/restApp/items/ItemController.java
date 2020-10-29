package com.zabozhan.restApp.items;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/items")
    List<Item> all(){
        return repository.findAll();
    }

    @PostMapping("/items")
    Item newItem(@RequestBody Item item){
        return repository.save(item);
    }

    @GetMapping("/items/{id}")
    Item oneItem(@PathVariable long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PutMapping("/items/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable long id){

        return repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setCategory(newItem.getCategory());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });
    }

    @DeleteMapping("/items/{id}")
    void deleteItem(@PathVariable long id){
        repository.deleteById(id);
    }
}
