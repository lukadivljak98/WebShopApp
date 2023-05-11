package org.unibl.etf.webshop.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserSupportMessageRequest {
    private String message;
    private String senderUsername;
}
