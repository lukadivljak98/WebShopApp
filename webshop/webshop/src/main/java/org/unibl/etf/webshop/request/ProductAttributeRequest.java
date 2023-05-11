package org.unibl.etf.webshop.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductAttributeRequest {
    private String value;
    private Long attributeId;
    private Long productId;

    public ProductAttributeRequest() {
    }
}
