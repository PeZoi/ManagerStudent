package com.example.projectfinal.service;

import com.example.projectfinal.dto.ParentDTO;
import com.example.projectfinal.dto.TeacherDTO;
import com.example.projectfinal.entity.Parent;
import com.example.projectfinal.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParentService {
    public ParentDTO getParent(int idParent);
    public List<ParentDTO> getAllParents();
    public ParentDTO saveParent(Parent parent);
    public void deleteParent(int idParent);
}
