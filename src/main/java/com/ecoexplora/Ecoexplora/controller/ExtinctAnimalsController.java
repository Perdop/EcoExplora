package com.ecoexplora.Ecoexplora.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecoexplora.Ecoexplora.model.ExtinctAnimals;
import com.ecoexplora.Ecoexplora.model.Users;
import com.ecoexplora.Ecoexplora.repository.EcoExploraRepositoryExtinctAnimals;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*") // Permite requisições de qualquer domínio
@RestController

public class ExtinctAnimalsController {
	@Autowired
	EcoExploraRepositoryExtinctAnimals ecoexploraRepositoryAnimals;
	
	@GetMapping("/getAllExtinctAnimals")
	public List<ExtinctAnimals> getAllAnimais(){
		return ecoexploraRepositoryAnimals.findAll();
	}
	
    @DeleteMapping("/removeExtinctAnimals/{identity}")
    public boolean deleteRow(@PathVariable("identity") Integer id){
        if(!ecoexploraRepositoryAnimals.findById(id).equals(Optional.empty())){
        	ecoexploraRepositoryAnimals.deleteById(id);
            return true;
        }
        return false;
    }
    
    @PutMapping("/updateExtinctAnimals/{identity}")
    public ExtinctAnimals updateAnimal(@PathVariable("identity") Integer id,
                                        @RequestBody Map<String, Object> body) {

        ExtinctAnimals current = ecoexploraRepositoryAnimals.findById(id).get();
        
        current.setName((String) body.get("name"));
        current.setAnimalType((Integer) body.get("animalType"));
        current.setAbout((String) body.get("about"));
        current.setLiving((Integer) body.get("living"));
        current.setState((String) body.get("state"));
        current.setAnimalPhoto((String) body.get("animalPhoto"));
        
        ecoexploraRepositoryAnimals.save(current);
        
        return current;
    }

    
    @PostMapping("/addExtinctAnimals")
    public ExtinctAnimals create(@RequestBody Map<String, Object> body) {

        String name = (String) body.get("name");
        Integer animalType = (Integer) body.get("animalType");
        String about = (String) body.get("about");
        Integer living = (Integer) body.get("living");
        String state = (String) body.get("state");
        String animalPhoto = (String) body.get("animalPhoto");

        ExtinctAnimals newAnimal = new ExtinctAnimals(name, animalType, about, living, state, animalPhoto);

        return ecoexploraRepositoryAnimals.save(newAnimal);
    }
	
	@PostMapping("/addMultipleExtinctAnimals")
	public List<ExtinctAnimals> createMultiple(@RequestBody List<Map<String, Object>> animals) {
	    List<ExtinctAnimals> createdAnimals = new ArrayList<>();
	
	    for (Map<String, Object> body : animals) {
	        String name = (String) body.get("name");
	        Integer animalType = (Integer) body.get("animalType");
	        String about = (String) body.get("about");
	        Integer living = (Integer) body.get("living");
	        String state = (String) body.get("state");
	        String animalPhoto = (String) body.get("animalPhoto");
	
	        ExtinctAnimals newAnimal = new ExtinctAnimals(name, animalType, about, living, state, animalPhoto);
	        createdAnimals.add(ecoexploraRepositoryAnimals.save(newAnimal));
	    }
	
	    return createdAnimals;
	}


}
