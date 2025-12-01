package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    public Map<Integer, Koala> koalas = new HashMap<>();

        @GetMapping
        public List<Koala> getAllKangaroos(){
            return koalas.values().stream().toList();
        }
        @GetMapping("/{id}")
        public Koala getKangarooById(@PathVariable("id") int id){
            Koala foundById = this.koalas.get(id);
            if(foundById == null){
                throw new ZooException("ID is not found!", HttpStatus.NOT_FOUND);
            }
            return foundById;
        }
        @PostMapping
        public Koala addKoala(@RequestBody Koala newKoala){
            if(koalas.containsKey(newKoala.getId())){
                throw new ZooException("Koala already exists!", HttpStatus.BAD_REQUEST);
            }
            koalas.put(newKoala.getId(), newKoala);
            return  newKoala;
        }
        @PutMapping("/{id}")
        public Koala updateKoala(@PathVariable("id")int id,
                                       @RequestBody Koala updatedKoala){
            if(!koalas.containsKey(id)){
                throw new ZooException("Koala is not found", HttpStatus.NOT_FOUND);
            }
            updatedKoala.setId(id);
            koalas.put(id, updatedKoala);
            return  updatedKoala;
        }
        @DeleteMapping("/{id}")
        public Koala removedKoala(@PathVariable("id") int id){
            Koala removedKoala = this.koalas.remove(id);
            if(removedKoala == null){
                throw new ZooException("Kangaroo does not exist", HttpStatus.NOT_FOUND);
            }
            return  removedKoala;
        }
    }
