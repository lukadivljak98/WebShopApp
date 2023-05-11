package org.unibl.etf.webshop.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductRequest {
    private String category;
    private String description;
    private String location;
    private boolean isNew;
    private String price;
    private String seller;
    private String title;
    private MultipartFile[] images;
}
