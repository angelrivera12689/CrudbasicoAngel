package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.AssistantDTO;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.IAssistant;

@Service
public class AssistantService {

    @Autowired
    private IAssistant data;

    public void save(AssistantDTO assistantDTO) {
        Assistant assistant = convertToModel(assistantDTO);
        data.save(assistant);
    }

    public AssistantDTO convertToDTO(Assistant assistant) {
        return new AssistantDTO(
                assistant.getName(),
                assistant.getEmail(),
                assistant.getPhone());
    }

    public Assistant convertToModel(AssistantDTO assistantDTO) {
        return new Assistant(
                0, // Assuming ID is auto-generated
                assistantDTO.getName(),
                assistantDTO.getEmail(),
                assistantDTO.getPhone());
    }
}
