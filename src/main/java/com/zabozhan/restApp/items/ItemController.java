package com.zabozhan.restApp.items;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ItemController {

    private final ItemRepository repository;

    private final ItemModelAssembler assembler;

    public ItemController(ItemRepository repository, ItemModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/items")
    CollectionModel<EntityModel<Item>> all(){
        List<EntityModel<Item>> items = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(items,linkTo(methodOn(ItemController.class).all()).withSelfRel());
    }

    @PostMapping("/items")
    Item newItem(@RequestBody Item item){
        return repository.save(item);
    }

    @GetMapping("/items/{id}")
    EntityModel <Item> oneItem(@PathVariable long id) {
        Item item =  repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return assembler.toModel(item);
    }

    @PutMapping("/items/{id}")
    ResponseEntity<?> replaceItem(@RequestBody Item newItem, @PathVariable long id){

        Item updatedItem = repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setCategory(newItem.getCategory());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });

        EntityModel<Item> entityModel = assembler.toModel(updatedItem);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/items/{id}")
    ResponseEntity<?> deleteItem(@PathVariable long id){
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
