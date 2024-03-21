package com.admin.admin.services;

import com.admin.admin.entities.users.Customer;
import com.admin.admin.entities.users.DTO.sellerdto;
import com.admin.admin.entities.users.PagingDefinationDTO;
import com.admin.admin.entities.users.Seller;
import com.admin.admin.entities.users.customerdto;
import com.admin.admin.exception.BadRequestException;
import com.admin.admin.exception.NotFoundException;
import com.admin.admin.exception.SuccessResponse;
import com.admin.admin.repository.RegisterCustomerRepository;
import com.admin.admin.repository.RegisterSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    RegisterCustomerRepository registerCustomerRepository;

    @Autowired
    RegisterSellerRepository registerSellerRepository;



    @Autowired
    EmailSenderService emailSenderService;



    public Page<customerdto> readAllCustomer(PagingDefinationDTO pagingDefinationDTO) {
        Pageable pageable = PageRequest.of(pagingDefinationDTO.getOffSet(), pagingDefinationDTO.getPageSize(), (Sort.by(new Sort.Order(null, pagingDefinationDTO.getSortBY()))));
        Page<customerdto> customers = registerCustomerRepository.findAllCustomerPartialData(pageable);
        if (customers == null) throw new NotFoundException("Not Found any customer");
        return customers;
    }

    public Page<sellerdto> readAllSeller(PagingDefinationDTO pagingDefinationDTO) {
        Pageable pageable = PageRequest.of(pagingDefinationDTO.getOffSet(), pagingDefinationDTO.getPageSize(), (Sort.by(new Sort.Order(null, pagingDefinationDTO.getSortBY()))));
        Page<sellerdto> sellers = registerSellerRepository.findAllSellerPartialData(pageable);
        if (sellers.isEmpty())
            throw new NotFoundException("No any seller");
        return sellers;
    }


    public ResponseEntity<SuccessResponse> activateCustomer(Long customerId) {
        Optional<Customer> customer = registerCustomerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new NotFoundException("User Not Found");
        if (!customer.get().getIsActive()) {
            customer.get().setIsActive(true);
            try {
                registerCustomerRepository.save(customer.get());
            } catch (Exception ex) {
                throw new BadRequestException(ex.getMessage());
            }
            SimpleMailMessage mailMessage = emailSenderService
                    .CreateBodyForMail(customer.get().getEmailId(), "Activate Account!", "Your Customer Account is Activated");
            emailSenderService.sendEmail(mailMessage);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Customer Activate Successfully"));
        } else
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Customer Already Activate"));
    }

    public ResponseEntity<SuccessResponse> deActivateCustomer(Long customerId) {
        Optional<Customer> customer = registerCustomerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new NotFoundException("User Not Found");
        if (!customer.get().getIsActive())
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Customer Already Deactivate"));
        customer.get().setIsActive(false);
        try {
            registerCustomerRepository.save(customer.get());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException(ex.getMessage());
        }
        SimpleMailMessage mailMessage = emailSenderService
                .CreateBodyForMail(customer.get().getEmailId(), "Deactivate Account!", "Your Account is Deactivate");
        emailSenderService.sendEmail(mailMessage);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Customer  Deactivate Successfully"));
    }

    public ResponseEntity<SuccessResponse> activateSeller(Long sellerId) {
        Optional<Seller> seller = registerSellerRepository.findById(sellerId);

        if (seller.isEmpty())
            throw new NotFoundException("Seller id is invalid");

        if (seller.get().getIsActive())
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Seller Already Activate"));
        seller.get().setIsActive(true);
        try {
            registerSellerRepository.save(seller.get());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SuccessResponse(ex.getMessage()));
        }
        SimpleMailMessage mailMessage = emailSenderService
                .CreateBodyForMail(seller.get().getEmailId(), "Activate Account!", "Your Seller Account is Activated");
        emailSenderService.sendEmail(mailMessage);
        //URL myURL = new URL;
        //  return ResponseEntity(new SuccessResponse(new Date(),"Seller Activate Successfully"), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Seller Activate Successfully"));
    }

    public ResponseEntity<SuccessResponse> deActivateSeller(Long sellerId) {
        Optional<Seller> seller = registerSellerRepository.findById(sellerId);
        // System.out.println(">>>"+seller.get().getCompanyName());
        if (seller.isEmpty())
            throw new NotFoundException("Seller id is invalid");
        if (!seller.get().getIsActive()) {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Seller Already Deactivate"));
        } else {
            seller.get().setIsActive(false);
            try {
                //  System.out.println(">>>"+seller.get().getCompanyName());
                registerSellerRepository.save(seller.get());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new BadRequestException(ex.getMessage());
            }
            SimpleMailMessage mailMessage = emailSenderService
                    .CreateBodyForMail(seller.get().getEmailId(), "Deactivate Account!", "Your Seller Account is Deactivated");
            emailSenderService.sendEmail(mailMessage);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Seller Deactivate Successfully"));
        }
    }
}
