package com.moneyBank.moneyBank.requestDtos;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateAccountRequest extends BaseAccountRequest{


    @NotBlank(message = "Account id must not be empty")
    private String id;
}
