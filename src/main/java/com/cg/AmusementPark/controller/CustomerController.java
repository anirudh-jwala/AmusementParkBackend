package com.cg.AmusementPark.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.AmusementPark.entities.Customer;
import com.cg.AmusementPark.exception.CustomerNotFoundException;
import com.cg.AmusementPark.exception.InvalidCustomerException;
import com.cg.AmusementPark.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Add a new customer record to database
	 * 
	 * @PostMapping public ResponseEntity<Customer>
	 *              insertCustomer(@Valid @RequestBody Customer customer,
	 *              BindingResult bindingResult) throws CustomerExistsException,
	 *              InvalidCustomerException {
	 * 
	 *              logger.info("Called POST mapping insertCustomer() method");
	 * 
	 *              if (bindingResult.hasErrors()) { throw new
	 *              InvalidCustomerException("Customer you are trying to add is not
	 *              give valid details"); }
	 * 
	 *              return new
	 *              ResponseEntity<>(customerService.insertCustomer(customer),
	 *              HttpStatus.CREATED);
	 * 
	 *              }
	 */

	/**
	 * Update an existing record of customer in database
	 */
	@PutMapping
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult)
			throws CustomerNotFoundException, InvalidCustomerException {

		logger.info("Called PUT mapping updateCustomer() method");

		if (bindingResult.hasErrors()) {
			throw new InvalidCustomerException("Customer information provided is not valid, please try again!");
		}

		return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.OK);

	}

	/**
	 * Delete an existing customer record in database, else throw
	 * CustomerNotFoundException
	 */
	@DeleteMapping(path = "/{customerId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Long customerId)
			throws CustomerNotFoundException {

		logger.info("Called DELETE mapping deleteCustomer() method");

		return new ResponseEntity<>(customerService.deleteCustomer(customerId), HttpStatus.OK);

	}

	/**
	 * Get list of all customers available in database
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Customer>> viewCustomers() throws CustomerNotFoundException {

		logger.info("Called GET mapping viewCustomers() method");

		return new ResponseEntity<>(customerService.viewCustomers(), HttpStatus.OK);

	}

	/**
	 * Get a specific custom based on the provided customer id
	 */
	@GetMapping(path = "/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<Customer> viewCustomer(@PathVariable("customerId") Long customerId)
			throws CustomerNotFoundException {

		logger.info("Called GET mapping viewCustomer() method");

		return new ResponseEntity<>(customerService.viewCustomer(customerId), HttpStatus.OK);

	}

	/**
	 * Validate the customer record based on email id and password
	 */
	@PostMapping(path = "/auth")
	public ResponseEntity<Customer> validateCustomer(@RequestBody Customer customer, BindingResult bindingResult)
			throws CustomerNotFoundException, InvalidCustomerException {

		logger.info("Called POST mapping validateCustomer() method");

		return new ResponseEntity<>(customerService.validateCustomer(customer.getEmail(), customer.getPassword()),
				HttpStatus.OK);

	}

}
