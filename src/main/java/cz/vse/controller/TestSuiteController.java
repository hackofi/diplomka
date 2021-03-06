package cz.vse.controller;

import cz.vse.dto.SuiteForm;
import cz.vse.entity.TestSuite;
import cz.vse.service.PersonService;
import cz.vse.service.ProjectService;
import cz.vse.service.SuiteService;
import cz.vse.service.TCMusterService;
import cz.vse.utils.SecurityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by pcejka on 09.10.2016.
 */
@Controller
@RequestMapping("/suite")
public class TestSuiteController {
    private final Logger l = Logger.getLogger(this.getClass());
    @Autowired
    ProjectService projectService;

    @Autowired
    PersonService personService;

    @Autowired
    TCMusterService tcMusterService;

    @Autowired
    SuiteService suiteService;

    @Autowired
    SecurityUtils securityUtils;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createTestSuite(Model model, SuiteForm suiteForm, @RequestParam(required = false, value = "project") Long projectId) {
        l.info("/suite/create get");
        Long personId = securityUtils.getLoggedPersonId();

        if (projectId != null) {
            suiteForm.setProject_id(projectId);
        }
        model.addAttribute("usersProjects", projectService.findAllTestProjectNamesByUserId(personId));
        return "suiteCreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProject(Model model, @Valid SuiteForm suiteForm, BindingResult bindingResult) {
        l.info("/suite/create post");

        if (bindingResult.hasErrors()) {
            l.error("form has errors");
            Long personId = securityUtils.getLoggedPersonId();
            Long suiteId = suiteForm.getId();
            TestSuite testSuite = suiteService.findTestSuiteById(suiteId);
            l.error("Id projektu: " + suiteId);
            model.addAttribute("tcMustersByProject", tcMusterService.findTCMusterNamesByProjectId(testSuite.getProject().getId()));
            model.addAttribute("usersProjects", projectService.findAllTestProjectNamesByUserId(personId));
            model.addAttribute("tcMusterNames", tcMusterService.findAllTestCaseMusterNamesByTestSuiteId(suiteService.findTestSuiteDTOById(suiteId).getId()));
            return "suiteCreate";
        }


        if (suiteForm.getId() == null) {
            suiteService.createTestSuite(suiteForm);
        } else {
            suiteService.updateTestSuite(suiteForm);
        }
        return "redirect:/suite/suites-by-project/" + suiteForm.getProject_id();
    }

    @RequestMapping("/edit/{id}")
    public String editTestSuite(@PathVariable("id") int id, Model model) {
        l.info("/suite/edit/" + id);
        Long personId = securityUtils.getLoggedPersonId();
        SuiteForm suiteForm = suiteService.findTestSuiteFormById(id);

        model.addAttribute("suiteForm", suiteForm);
        model.addAttribute("tcMustersByProject", tcMusterService.findTCMusterNamesByProjectId(suiteForm.getProject_id()));
        model.addAttribute("usersProjects", projectService.findAllTestProjectNamesByUserId(personId));
        model.addAttribute("tcMusterNames", tcMusterService.findAllTestCaseMusterNamesByTestSuiteId(suiteService.findTestSuiteDTOById(id).getId()));
        return "suiteCreate";
    }

    @RequestMapping("/remove/{id}")
    public String removeSuite(@PathVariable("id") int id, HttpServletRequest request) {
        l.info("/suite/remove/" + id);
        suiteService.deleteTestSuiteById(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping("/suites-by-project/{id}")
    public String suitesByProject(@PathVariable("id") long id, Model model) {
        l.info("/suites/suites-by-project/{id} - " + id);

        model.addAttribute("suites", suiteService.findAllTestSuiteListsByProjectId(id));
        model.addAttribute("project", projectService.findTestProjectNameById(id));
        return "suites";
    }

    @RequestMapping(value = "/suites", method = RequestMethod.GET)
    public String suitesAllShow(Model model) {
        l.info("/suite/suites}");
        model.addAttribute("suites", suiteService.findAllTestSuitesDTOByUser(securityUtils.getLoggedPerson()));
        return "suites";
    }


}

