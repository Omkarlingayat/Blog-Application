package com.blog.services.impl;

import com.blog.entities.Categorie;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategorieDTO;
import com.blog.repositories.CategorieRepository;
import com.blog.services.CategorieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorieServiceImpl implements CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ModelMapper modelMapper;

    // create categorie
    @Override
    public ApiResponse createCategorie(CategorieDTO categorieDTO) {
        Categorie categorie = categorieDtoToCategorie(categorieDTO);
        categorieRepository.save(categorie);
        return new ApiResponse("Categorie created successfully",true);
    }

    // update categorie
    @Override
    public ApiResponse updateCategorie(CategorieDTO categorieDTO, Integer categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(()-> new ResourceNotFoundException("categorie","id",categorieId));

        if(categorieDTO.getCategorieTitle() != null){
            categorie.setCategorieTitle(categorieDTO.getCategorieTitle());
        }
        if(categorieDTO.getCategorieDescription() != null){
            categorie.setCategorieDescription(categorieDTO.getCategorieDescription());
        }

        categorieRepository.save(categorie);

        return new ApiResponse("Categorie updated successfully",true);
    }

    // delete categorie
    @Override
    public void deleteCategorie(Integer categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(()-> new ResourceNotFoundException("categorie","id",categorieId));
        categorieRepository.delete(categorie);
    }

    // get categore by its id
    @Override
    public CategorieDTO getCategorieById(Integer categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(()->new ResourceNotFoundException("categorie","id",categorieId));
        CategorieDTO categorieDTO = categorieToCategorieDto(categorie);
        System.out.println("------------ categorydto = "+categorieDTO);
        return categorieDTO;
    }

    // get all categories
    @Override
    public List<CategorieDTO> getAllCategories() {
        List<Categorie> categories = categorieRepository.findAll();
        List<CategorieDTO> categorieDTOList = categories.stream().map(categorie -> this.categorieToCategorieDto(categorie)).collect(Collectors.toList());
        return categorieDTOList;
    }

    // convert categorie into categoreDTO
    public CategorieDTO categorieToCategorieDto(Categorie categorie){
        CategorieDTO categorieDTO = modelMapper.map(categorie, CategorieDTO.class);
        return categorieDTO;
    }

    // convert categorieDto into categorie
    public Categorie categorieDtoToCategorie(CategorieDTO categorieDTO){
        Categorie categorie = modelMapper.map(categorieDTO, Categorie.class);
        return  categorie;
    }
}
