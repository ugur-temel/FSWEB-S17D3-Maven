package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    public Map<Integer, Kangaroo> kangaroos = new HashMap<>();

    @GetMapping
    public List<Kangaroo> getAllKangaroos(){
        return kangaroos.values().stream().toList();
    }
    @GetMapping("/{id}")
    public Kangaroo getKangarooById(@PathVariable("id") int id){
        Kangaroo foundById = this.kangaroos.get(id);
        if(foundById == null){
            throw new ZooException("ID is not found!", HttpStatus.NOT_FOUND);
        }
        return foundById;
    }
    @PostMapping
    public Kangaroo addKangaroo(@RequestBody Kangaroo newKangaroo){
        if (newKangaroo == null || newKangaroo.getId() <= 0 ||
                newKangaroo.getName() == null || newKangaroo.getName().isEmpty() ||
                newKangaroo.getHeight() <= 0 || newKangaroo.getWeight() <= 0 ||
                newKangaroo.getGender() == null || newKangaroo.getGender().isEmpty()) {
            throw new ZooException("Invalid kangaroo data", HttpStatus.BAD_REQUEST);
        }

        if(kangaroos.containsKey(newKangaroo.getId())){
            throw new ZooException("Kangaroo already exists!", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(newKangaroo.getId(), newKangaroo);
        return  newKangaroo;
    }
    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable("id")int id,
                                   @RequestBody Kangaroo updatedKangaroo){
        if(!kangaroos.containsKey(id)){
            throw new ZooException("Kangaroo is not found", HttpStatus.NOT_FOUND);
        }

        updatedKangaroo.setId(id);
        kangaroos.put(id, updatedKangaroo);
        return  updatedKangaroo;
    }
    @DeleteMapping("/{id}")
    public Kangaroo removedKangaroo(@PathVariable("id") int id){
        Kangaroo removedKangaroo = this.kangaroos.remove(id);
        if(removedKangaroo == null){
            throw new ZooException("Kangaroo does not exist", HttpStatus.NOT_FOUND);
        }
        return  removedKangaroo;
    }
}
