package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategorieDTO;
import com.blog.services.CategorieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorie")
public class CategoriController {
    @Autowired
    private CategorieService categorieService;

    // create categorie
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategorie(@Valid @RequestBody CategorieDTO categorieDTO){
        ApiResponse response = categorieService.createCategorie(categorieDTO);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
    }

    // update categorie
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategorie(@RequestBody CategorieDTO categorieDTO, @PathVariable Integer id){
        ApiResponse response = categorieService.updateCategorie(categorieDTO,id);
        return ResponseEntity.ok(response);
    }

    // delete categorie
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCaategorie(@PathVariable Integer id){
        categorieService.deleteCategorie(id);
        ApiResponse apiResponse = new ApiResponse("Categorie deleted successfully", true);
        return ResponseEntity.ok(apiResponse);
    }

    // get categorie by its id
    @GetMapping("/get/{id}")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Integer id){
        CategorieDTO categorieDTO = categorieService.getCategorieById(id);
        return ResponseEntity.ok(categorieDTO);
    }

    // get all categories
    @GetMapping("/get")
    public ResponseEntity<List<CategorieDTO>> getAllCategories(){
        List<CategorieDTO> list = categorieService.getAllCategories();
        return ResponseEntity.ok(list);
    }
}
