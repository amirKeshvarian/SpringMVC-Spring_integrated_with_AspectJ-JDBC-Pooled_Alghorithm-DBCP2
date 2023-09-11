package org.company.project.controller;

import org.company.project.common.wrraper.ErrorHandler;
import org.company.project.model.entity.Person;
import org.company.project.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller//Spring MVC RequestBase
@Scope("singleton")
@Lazy(value = false)//Eager
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @RequestMapping("/save.do")
    public String save (@ModelAttribute Person person, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            personService.save(person);
        } catch (Exception e) {
            request.setAttribute("error", ErrorHandler.getError(e));
            try {
                response.sendError(700);
            } catch (IOException ex) {
                return null;
            }
        }
        return "redirect:/person/findAll.do";
    }

    @RequestMapping("/remove.do")
    public String remove (@ModelAttribute Person person, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            personService.remove(person);
            return "redirect:/person/findAll.do";
        } catch (Exception e) {
            request.setAttribute("error", ErrorHandler.getError(e));
            try {
                response.sendError(700);
            } catch (IOException ex) {
                return null;
            }
        }
        return null;
    }

    @RequestMapping("/change.do")
    public String change (@ModelAttribute Person person, HttpServletRequest request, HttpServletResponse response){
        try {
            personService.change(person);
            return "redirect:/person/findAll.do";
        } catch (Exception e) {
            request.setAttribute("error", ErrorHandler.getError(e));
            try {
                response.sendError(700);
            } catch (IOException ex) {
                return null;
            }
        }
        return null;
    }

    @RequestMapping("/findOne.do")
    public String findOne(@ModelAttribute Person person, HttpServletRequest request, HttpServletResponse response){
        try {
            request.setAttribute("dbPerson", personService.findOne(person));
        } catch (Exception e) {
            request.setAttribute("error", ErrorHandler.getError(e));
            try {
                response.sendError(700);
            } catch (IOException ex) {
                return null;
            }
        }
        return "person/index";

    }

    @RequestMapping("/findAll.do")
    public String findAll (HttpServletRequest request, HttpServletResponse response)
    {
        try {
            request.setAttribute("list", personService.findAll());
            return "person/index";
        } catch (Exception e) {
            request.setAttribute("error", ErrorHandler.getError(e));
            try {
                response.sendError(700);
            } catch (IOException ex) {
                return null;
            }
        }
        return null;
    }
}
