package com.app.banking.dto;

//import lombok.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class AccountDto {
//    private Long id;
//    private String accountHolderName;
//    private double balance;
//}

public record AccountDto(Long id,
                         String accountHolderName,
                         double balance)
{

}
