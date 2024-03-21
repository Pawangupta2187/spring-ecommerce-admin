package com.admin.admin.controllers;

import com.admin.admin.entities.users.DTO.sellerdto;
import com.admin.admin.entities.users.PagingDefinationDTO;
import com.admin.admin.entities.users.customerdto;
import com.admin.admin.exception.SuccessResponse;
import com.admin.admin.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;




    @GetMapping("/customers")
    public Page<customerdto> getAllCustomer(@RequestBody(required = false) PagingDefinationDTO pagingDefination) {
       if(pagingDefination==null)
           pagingDefination=new PagingDefinationDTO();
        return adminService.readAllCustomer(pagingDefination);

    }

    @GetMapping("/sellers")
    public Page<sellerdto> getAllSeller(@RequestBody(required = false) PagingDefinationDTO pagingDefination) {
        if(pagingDefination==null)
            pagingDefination=new PagingDefinationDTO();
        return adminService.readAllSeller(pagingDefination);

    }

    @PatchMapping("/activatecustomer/{id}")
    public ResponseEntity<SuccessResponse> activateCustomer(@PathVariable Long id) {
        return adminService.activateCustomer(id);
    }

    @PatchMapping("/deactivatecustomer/{id}")
    public ResponseEntity<SuccessResponse> deActivateCustomer(@PathVariable Long id) {
        return adminService.deActivateCustomer(id);
    }

    @PatchMapping("/activateseller/{id}")
    public ResponseEntity<SuccessResponse> activateSeller(@PathVariable Long id) {
        return adminService.activateSeller(id);
    }

    @PatchMapping("/deactivateseller/{id}")
    public ResponseEntity<SuccessResponse> deActivateSeller(@PathVariable Long id) {
        return adminService.deActivateSeller(id);
    }


}
