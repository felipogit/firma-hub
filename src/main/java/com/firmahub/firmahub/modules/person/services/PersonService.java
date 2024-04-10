package com.firmahub.firmahub.modules.person.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firmahub.firmahub.mapper.DozerMapper;
import com.firmahub.firmahub.modules.person.dtos.PersonDTO;
import com.firmahub.firmahub.modules.person.excepetions.ResourseNotFoundException;
import com.firmahub.firmahub.modules.person.model.Person;
import com.firmahub.firmahub.modules.person.repository.PersonRepository;

@Service
public class PersonService {
    
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people");
        return DozerMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);
        var person = personRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Person not found"));
        return DozerMapper.parseObject(person, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO payload) {
        logger.info("Create new task!");
        var entity = DozerMapper.parseObject(payload, Person.class);
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        return vo;
    }

    public PersonDTO update(PersonDTO task) {
        logger.info("Update task by id: " + task.getId());

        var entity = personRepository.findById(task.getId())
            .orElseThrow(() -> new ResourseNotFoundException("Task not found!"));

        entity.setFirstName(task.getFirstName());
        entity.setLastName(task.getLastName());
        entity.setAddress(task.getAddress());
        entity.setGender(task.getGender());

        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonDTO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Delete task by id: " + id);

        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourseNotFoundException("Task not found!"));
        
        personRepository.delete(entity);
    }
    
}
