package com.admin.admin.entities.cart.DTO;

import com.admin.admin.entities.products.DTO.ViewVariationDTO;
import lombok.Data;

@Data
public class ViewCartDTO {
    private Long quantity;
    private ViewVariationDTO variation;
    }
