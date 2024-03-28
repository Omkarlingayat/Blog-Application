package com.blog.services;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategorieDTO;

import java.util.List;

public interface CategorieService {
    // create categorie
    ApiResponse createCategorie(CategorieDTO categorieDTO);

    // update categorie
    ApiResponse updateCategorie(CategorieDTO categorieDTO, Integer categorieId);

    // delete categorie
    void deleteCategorie(Integer categorieId);

    // get categorie by its id
    CategorieDTO  getCategorieById(Integer categorieId);

    // get all categories
    List<CategorieDTO> getAllCategories();
}
