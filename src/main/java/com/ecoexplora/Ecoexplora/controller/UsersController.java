package com.ecoexplora.Ecoexplora.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecoexplora.Ecoexplora.model.Users;
import com.ecoexplora.Ecoexplora.repository.EcoexploraRepositoryUsers;

@RestController
public class UsersController {

	@Autowired
	EcoexploraRepositoryUsers ecoexploraRepositoryUsers;
	
	@GetMapping("/getAllUsers")
	public List<Users> getAllUsers(){
		return ecoexploraRepositoryUsers.findAll();
	}
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable("id") Integer id) {
	    Optional<Users> user = ecoexploraRepositoryUsers.findById(id);
	    if (user.isPresent()) {
	        return ResponseEntity.ok(user.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}

	@GetMapping("/getUser/{user}")
	public ResponseEntity<Users> getUserByUsername(@PathVariable("user") String user) {
	    Optional<Users> username = ecoexploraRepositoryUsers.findByUser(user);
	    
	    if (username.isPresent()) {
	        return ResponseEntity.ok(username.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
	
	@GetMapping("/findUser/{user}")
	public boolean findByUsername(@PathVariable("user") String user) {
	    Optional<Users> username = ecoexploraRepositoryUsers.findByUser(user);
	    return username.isPresent();
	}


	
    @DeleteMapping("/removeUser/{identity}")
    public boolean deleteRow(@PathVariable("identity") Integer id){
        if(!ecoexploraRepositoryUsers.findById(id).equals(Optional.empty())){
        	ecoexploraRepositoryUsers.deleteById(id);
            return true;
        }
        return false;
    }
    
    @PutMapping("/updateUser/{identity}")
    public Users updateAddress(@PathVariable("identity") Integer id,
                                 @RequestBody Map<String, String> body){

        Users current = ecoexploraRepositoryUsers.findById(id).get();
        current.setUser(body.get("user"));
        current.setPassword(body.get("password"));
        current.setUserPhoto(body.get("userPhoto"));
        ecoexploraRepositoryUsers.save(current);
        return current;
    }
    
    @PostMapping("/addUser")
    public Users create(@RequestBody Map<String, String> body){

        String user = body.get("user");;
        String password = body.get("password");
        String userPhoto = body.get("userPhoto");
        Users newLogins = new Users(user, password, userPhoto);
        return ecoexploraRepositoryUsers.save(newLogins);
    }
    
}

