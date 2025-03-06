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
import com.ecoexplora.Ecoexplora.model.Sightings;
import com.ecoexplora.Ecoexplora.repository.EcoExploraRepositorySightings;

@RestController

public class SightingsController {
	@Autowired
	EcoExploraRepositorySightings ecoexploraRepositorySightings;
	
	@GetMapping("/getAllSightings")
	public List<Sightings> getAllAnimais(){
		return ecoexploraRepositorySightings.findAll();
	}
	
    @DeleteMapping("/removeSightings/{identity}")
    public boolean deleteRow(@PathVariable("identity") Integer id){
        if(!ecoexploraRepositorySightings.findById(id).equals(Optional.empty())){
        	ecoexploraRepositorySightings.deleteById(id);
            return true;
        }
        return false;
    }
    
    @PutMapping("/updateSightings/{identity}")
    public Sightings updateSightings(@PathVariable("identity") Integer id,
                                        @RequestBody Map<String, Object> body) {

        Sightings current = ecoexploraRepositorySightings.findById(id).get();
        
        current.setUser((String) body.get("user"));
        current.setLocation((String) body.get("location"));
        current.setDate((String) body.get("date"));
        current.setPhoto((String) body.get("photo"));
	current.setAnimal((String) body.get("animal"));
        
        ecoexploraRepositorySightings.save(current);
        
        return current;
    }

    
    @PostMapping("/addSightings")
    public Sightings create(@RequestBody Map<String, Object> body) {

        String user = (String) body.get("user");
        String location = (String) body.get("location");
        String date = (String) body.get("date");
        String photo = (String) body.get("photo");
	String animal = (String) body.get("animal");

        Sightings newSightings = new Sightings(user, location, date, photo, animal);

        return ecoexploraRepositorySightings.save(newSightings);
    }

}
