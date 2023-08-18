package com.example.projectfinal.service.implement;

import com.example.projectfinal.dto.ParentDTO;
import com.example.projectfinal.dto.SubjectDTO;
import com.example.projectfinal.entity.Parent;
import com.example.projectfinal.entity.Subject;
import com.example.projectfinal.repository.ParentRepository;
import com.example.projectfinal.repository.SubjectRepository;
import com.example.projectfinal.service.ParentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParentImple implements ParentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    private ParentRepository parentRepository;

    public ParentImple(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }
    @Override
    public ParentDTO getParent(int idParent) {
        Optional<Parent> parent = parentRepository.findById(idParent);
        if (parent.isEmpty()) {
            return null;
        } else {
            ParentDTO parentDTO = modelMapper.map(parent.get(), ParentDTO.class);
            return parentDTO;
        }
    }

    @Override
    public List<ParentDTO> getAllParents() {
        List<Parent> parents = parentRepository.findAll();
        List<ParentDTO> parentDTOs = new ArrayList<>();
        for (Parent parent : parents) {
            ParentDTO parentDTO = modelMapper.map(parent, ParentDTO.class);
            parentDTOs.add(parentDTO);
        }
        return parentDTOs;
    }

    @Override
    @Transactional
    public ParentDTO saveParent(Parent parent) {
        return null;
    }

    @Override
    @Transactional
    public void deleteParent(int idParent) {

    }
}
