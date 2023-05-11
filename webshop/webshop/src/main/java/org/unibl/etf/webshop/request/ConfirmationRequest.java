package org.unibl.etf.webshop.request;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ConfirmationRequest {
    private String token;
}
