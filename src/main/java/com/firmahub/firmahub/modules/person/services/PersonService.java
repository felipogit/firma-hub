package com.firmahub.firmahub.modules.person.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.firmahub.firmahub.mapper.DozerMapper;
import com.firmahub.firmahub.modules.person.controllers.PersonController;
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
        var persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonDTO.class);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).fingById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by id: " + id);
        var person = personRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Person not found"));
        var vo = DozerMapper.parseObject(person, PersonDTO.class);
        vo.add(linkTo(methodOn(PersonController.class).fingById(id)).withSelfRel());
        return vo;
    }

    public PersonDTO create(PersonDTO payload) {
        logger.info("Create new task!");
        var entity = DozerMapper.parseObject(payload, Person.class); // Mapeando DTO para entidade
        entity = personRepository.save(entity); // Salvando a entidade no banco de dados
        var vo = DozerMapper.parseObject(entity, PersonDTO.class);
        vo.add(linkTo(methodOn(PersonController.class).fingById(vo.getKey())).withSelfRel());
        return vo;
    }
    

    public PersonDTO update(PersonDTO task) {
        logger.info("Update task by id: " + task.getKey());

        var entity = personRepository.findById(task.getKey())
            .orElseThrow(() -> new ResourseNotFoundException("Task not found!"));

        entity.setFirstName(task.getFirstName());
        entity.setLastName(task.getLastName());
        entity.setAddress(task.getAddress());
        entity.setGender(task.getGender());

        var vo = DozerMapper.parseObject(entity, PersonDTO.class);
        vo.add(linkTo(methodOn(PersonController.class).fingById(vo.getKey())).withSelfRel());
        return vo;
       
    }

    public void delete(Long id) {
        logger.info("Delete task by id: " + id);

        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourseNotFoundException("Task not found!"));
        
        personRepository.delete(entity);
    }
    
}
