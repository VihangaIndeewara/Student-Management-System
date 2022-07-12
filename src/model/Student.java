package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private String id;
    private String name;
    private String email;
    private String contact;
    private String address;
    private String nic;
}
