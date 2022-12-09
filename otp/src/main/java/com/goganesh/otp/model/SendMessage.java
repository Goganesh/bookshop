package com.goganesh.otp.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SendMessage {

    private String payload;
    private String contact;
}
