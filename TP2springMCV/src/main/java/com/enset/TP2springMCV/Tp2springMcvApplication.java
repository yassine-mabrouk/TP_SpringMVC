package com.enset.TP2springMCV;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.enset.TP2springMCV.entities.Patient;
import com.enset.TP2springMCV.repositories.PatientRepository;

@SpringBootApplication
public class Tp2springMcvApplication implements CommandLineRunner  {
  
	@Autowired
   private PatientRepository patientRepository;
   
	public static void main(String[] args) {
		SpringApplication.run(Tp2springMcvApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	
		
		patientRepository.findAll().forEach(p->{
		    System.out.println(p.toString());  
		});
		
		
	
	}
	
	
}
