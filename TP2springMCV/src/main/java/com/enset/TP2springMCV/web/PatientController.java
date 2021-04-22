package com.enset.TP2springMCV.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enset.TP2springMCV.entities.Patient;
import com.enset.TP2springMCV.repositories.PatientRepository;


@Controller
public class PatientController {
	 @Autowired
	  private PatientRepository patientRepository  ;
	 
	 
	 @GetMapping(path="/index")
		public String index() {
			return "index";
			}
	 
  @GetMapping(path="/deletePatient")
	public String delete(Long id ,RedirectAttributes redirectAttributes) {
	  patientRepository.deleteById(id);

	  redirectAttributes.addFlashAttribute("success", "Patient supprimer avec succées");
	  return "redirect:/patients";
  }
 
 
 
  @GetMapping(path="/patients")
 	public String list(Model model,
 		@RequestParam(name="page",defaultValue = "0")int page,
 		@RequestParam(name="size",defaultValue = "5")int size,
 		@RequestParam(name="keyword",defaultValue = "")String mc) {
	   Page<Patient> pagepatients=patientRepository.findByNameContains(mc,PageRequest.of(page, size));
	 
	   model.addAttribute("patients",pagepatients.getContent());
	   model.addAttribute("pages",new int [pagepatients.getTotalPages()]);
 	   model.addAttribute("thisPage",page);
	   model.addAttribute("keyword",mc);
 	   return "patients";  
   }
  
  @GetMapping(path="/formPatient")
	public String formPatient(Model model ) {
	  model.addAttribute("patient",new Patient());
	  return "formPatient";  
 }
  
  @PostMapping(path="/savePatient")
  public String savePatient(@Validated Patient p ,BindingResult bind,
		  RedirectAttributes redirectAttributes ) {
	  if(bind.hasErrors()) return "formPatient";
	  patientRepository.save(p);
	  
	  redirectAttributes.addFlashAttribute("success", "Patient Ajouter avec succées");
	  	return "redirect:/patients";
  }
  @GetMapping(path="/editPatient")
  public String editPatient(Model model,Long id)
  {
  	 Patient patient = patientRepository.findById(id).get();
       model.addAttribute("patient", patient);
  	return "editPatient";
  }
  
  @PostMapping("/updatePatient")
  public String updatePatient(Model model, Patient patient,BindingResult bindingResult 
  		,RedirectAttributes redirectAttributes)
  {
  	if(bindingResult.hasErrors()) return "editPatient";
  	patientRepository.save(patient);
  	 model.addAttribute("flash","success");
  	redirectAttributes.addFlashAttribute("success", "Patient modifier avec succées");
  	return "redirect:/patients";
  }
  
}
